package com.example.test.service;

import com.example.test.model.*;
import com.example.test.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

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

            for (Section section : program.getSectionSet()) {
                log.info("New Section is being created");
                Section addingSection = new Section(section.getDescription(), section.getImageContent(), section.getName(), section.getOrder_index(), program);

                for (Activity activity : section.getActivitySet()) {
                    log.info("New Question is being created");
                    Question question = activity.getQuestion();

                    if (question != null) {
                        Set<Choice> choiceSet = new HashSet<>();
                        for (Choice choice : question.getChoiceSet()) {
                            log.info("New Choice is being created");
                            choice.setQuestion(question);
                            choiceSet.add(choice);
                        }
                        question.setChoiceSet(choiceSet);
                        question.setActivity(activity);
                        activity.setQuestion(question);
                        activity.setSection(addingSection);
                        addingSection.setActivitySet(new HashSet<>(Collections.singletonList(activity)));
                    } else if (activity.getHtmlContent() != null) {
                        log.info("New HTML content is being created");
                        activity.setHtmlContent(activity.getHtmlContent());
                        addingSection.setActivitySet(new HashSet<>(Collections.singletonList(activity)));
                    } else {
                        log.info("Both activity HTML content and Question are empty!");
                    }
                }

                current.getSectionSet().add(addingSection);
            }
            log.info("New program is being created");
            programRepository.save(current);
        } else {
            log.error("Program already existed");
        }

        return current;
    }

    @Transactional
    public Program updateExistingProgram(Program program) {
        Program current = programRepository.findByName(program.getName());

        if (current != null) {
            current.setName(program.getName());
            current.setDescription(program.getDescription());
            log.info("Existing program is being returned");
            for (Section section : program.getSectionSet()) {
                log.info("Existing Section is being updated");
                Section currentSection = sectionRepository.findBySection_Id(section.getSection_id());
                if (currentSection != null) {
                    currentSection.setDescription(section.getDescription());
                    currentSection.setName(section.getName());
                    currentSection.setImageContent(section.getImageContent());
                    currentSection.setOrder_index(section.getOrder_index());
                    currentSection.setProgram(program);

                    // find existing Activity
                    for(Activity act : currentSection.getActivitySet()){
                        for(Activity inputAct : section.getActivitySet()){
                            // need to update existing Activity
                        }
                    }

                    // insert new Activity for current Section I am working on
                    current.getSectionSet().add(currentSection);
                } else {
                    Section newSection = new Section(section.getDescription(), section.getImageContent(), section.getName(), section.getOrder_index(), program);
                    for (Activity activity : section.getActivitySet()) {
                        log.info("New Question is being created");
                        Question question = activity.getQuestion();

                        if (question != null) {
                            Set<Choice> choiceSet = new HashSet<>();
                            for (Choice choice : question.getChoiceSet()) {
                                log.info("New Choice is being created");
                                choice.setQuestion(question);
                                choiceSet.add(choice);
                            }
                            question.setChoiceSet(choiceSet);
                            question.setActivity(activity);
                            activity.setQuestion(question);
                            activity.setSection(newSection);
                            newSection.setActivitySet(new HashSet<>(Collections.singletonList(activity)));
                        } else if (activity.getHtmlContent() != null) {
                            log.info("New HTML content is being created");
                            activity.setHtmlContent(activity.getHtmlContent());
                            newSection.setActivitySet(new HashSet<>(Collections.singletonList(activity)));
                        } else {
                            log.info("Both activity HTML content and Question are empty!");
                        }
                    }
                    current.getSectionSet().add(newSection);
                }
            }
            log.info("Existing program is being updated");
            programRepository.save(current);
        } else {
            log.error("Program does not exist");
        }
        return current;
    }

    @Transactional
    public void deleteExistingProgram(long id) {
        programRepository.deleteById(id);
    }
}
