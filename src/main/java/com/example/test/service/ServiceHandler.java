package com.example.test.service;

import com.example.test.model.*;
import com.example.test.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceHandler {

    private static final Logger log = LoggerFactory.getLogger(ServiceHandler.class);

    private ProgramRepository programRepository;

    private SectionRepository sectionRepository;

    private ActivityRepository activityRepository;

    private QuestionRepository questionRepository;

    private ChoiceRepository choiceRepository;

    @Autowired
    public ServiceHandler(ProgramRepository programRepository, SectionRepository sectionRepository,
                          ActivityRepository activityRepository, QuestionRepository questionRepository, ChoiceRepository choiceRepository) {
        this.programRepository = programRepository;
        this.sectionRepository = sectionRepository;
        this.activityRepository = activityRepository;
        this.questionRepository = questionRepository;
        this.choiceRepository = choiceRepository;
    }

    public List<Program> findAllProgram() {
        return programRepository.findAll();
    }

    public Optional<Program> findByProgramId(long id) {
        return programRepository.findById(id);
    }

    @Transactional
    public Program creatNewProgram(Program program) {
        Program current = programRepository.findByName(program.getName());
        if (current == null) {
            current = new Program(program.getName(), program.getDescription());
            log.info("New program is being created");
            programRepository.save(current);

            // save section in db
            for (Section section : current.getSectionSet()) {
                log.info("New Section is being created");
                sectionRepository.save(new Section(section.getDescription(), section.getImageContent(), section.getName(), section.getOrder_index(), program));

                if (section.getImageContent() == null) {
                    for (Activity activity : section.getActivitySet()) {
                        log.info("New Question is being created");
                        Question question = activity.getQuestion();
                        questionRepository.save(question);
                        for (Choice choice : question.getChoiceSet()) {
                            log.info("New Choice is being created");
                            choiceRepository.save(choice);
                        }
                        log.info("New Activity is being created with multiple choice");
                        activityRepository.save(new Activity(activity.getQuestion(), section));
                    }

                } else {
                    log.info("New Activity is being created with html contents");
                    activityRepository.save(new Activity(Base64.getEncoder().encodeToString(section.getImageContent()), section));
                }
            }
        } else {
            log.error("Program already existed");
        }

        return program;
    }

    @Transactional
    public Program updateExistingProgram(Program program) {
        Program current = programRepository.findByName(program.getName());

        if (current != null) {
            log.info("Existing program is being returned");
            programRepository.save(program);

            // save section in db
            for (Section section : current.getSectionSet()) {
                log.info("New Section is being created");
                sectionRepository.save(section);

                if (section.getImageContent() == null) {
                    for (Activity activity : section.getActivitySet()) {
                        log.info("New Question is being created");
                        Question question = activity.getQuestion();
                        questionRepository.save(question);
                        for (Choice choice : question.getChoiceSet()) {
                            log.info("New Choice is being created");
                            choiceRepository.save(choice);
                        }
                        log.info("New Activity is being created with multiple choice");
                        activityRepository.save(new Activity(activity.getQuestion(), section));
                    }

                } else {
                    log.info("New Activity is being created with html contents");
                    activityRepository.save(new Activity(Base64.getEncoder().encodeToString(section.getImageContent()), section));
                }
            }
        } else {
            log.error("Program does not exist");
        }

        return program;
    }

    @Transactional
    public void deleteExistingProgram(long id) {
        programRepository.deleteById(id);
    }
}
