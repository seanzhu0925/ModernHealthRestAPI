package com.example.test;

import com.example.test.model.*;
import com.example.test.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.*;

@SpringBootApplication
public class TestApplication {

    private static final Logger log = LoggerFactory.getLogger(TestApplication.class);

    public static void main(String[] args) {

        // This is a demo test to test create a brand-new Program
        ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(TestApplication.class, args);
        ProgramRepository programRepository = configurableApplicationContext.getBean(ProgramRepository.class);
        SectionRepository sectionRepository = configurableApplicationContext.getBean(SectionRepository.class);
        ActivityRepository activityRepository = configurableApplicationContext.getBean(ActivityRepository.class);

        Program program = new Program("This is a Program", "description");
        Section section2 = new Section("This is a Section2", new byte[]{}, "section name1", 1, program);
        Section section1 = new Section("This is a Section1", new byte[]{}, "section name2", 2, program);

        Activity activityHTML = new Activity("This is a demo html contents", section2);

        Activity activityQuestion = new Activity();

        Question question = new Question("Question1", activityQuestion);

        Choice choice = new Choice(question, "choice");
        Choice choice1 = new Choice(question, "choice1");
        Choice choice2 = new Choice(question, "choice2");
        Choice choice3 = new Choice(question, "choice3");

        Set<Choice> choiceSet = new HashSet<>(Arrays.asList(choice, choice1, choice2, choice3));

        question.setChoiceSet(choiceSet);

        activityQuestion.setQuestion(question);
        activityQuestion.setSection(section1);

        section1.setActivitySet(new HashSet<>(Collections.singletonList(activityQuestion)));
        section2.setActivitySet(new HashSet<>(Collections.singletonList(activityHTML)));

        program.setSectionSet(new HashSet<>(Arrays.asList(section1, section2)));

        Program savedProgram = programRepository.save(program);
        log.info("Saved Program Id-----> " + savedProgram.getProgram_id() + "  Program Description-----> " + savedProgram.getDescription()
                + "  Program name-----> " + savedProgram.getName());

        List<Section> sectionList = sectionRepository.findByProgram(savedProgram);
        sectionList.forEach(current ->
                log.info("Saved Section Id-----> " + current.getSection_id() + "  Associated with Program Id-----> " + current.getProgram().getProgram_id() + "  Section Description-----> " + current.getDescription())
        );

        sectionList.forEach(sec -> {
            List<Activity> activityList = activityRepository.findBySection(sec);
            activityList.forEach(acti ->
                    log.info("Saved Activity Id-----> " + acti.getActivity_id() + "  Associated with Section Id-----> " + acti.getSection().getSection_id())
            );
        });
    }
}