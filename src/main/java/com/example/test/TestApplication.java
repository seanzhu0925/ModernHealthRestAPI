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

        ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(TestApplication.class, args);
        ProgramRepository programRepository = configurableApplicationContext.getBean(ProgramRepository.class);
        SectionRepository sectionRepository = configurableApplicationContext.getBean(SectionRepository.class);
        ActivityRepository activityRepository = configurableApplicationContext.getBean(ActivityRepository.class);
        QuestionRepository questionRepository = configurableApplicationContext.getBean(QuestionRepository.class);
        ChoiceRepository choiceRepository = configurableApplicationContext.getBean(ChoiceRepository.class);


        Program program = new Program("This is a Program", "description");
        Section section = new Section("This is a Section", new byte[]{}, "section name", 1, program);
        Section section1 = new Section("This is a Section1", new byte[]{}, "section name", 2, program);


        Activity activity = new Activity("activity 1", section);

        Activity activity1 = new Activity();


        Question question = new Question("Question1", activity);

        Choice choice = new Choice(question, "choice");
        Choice choice1 = new Choice(question, "choice1");
        Choice choice2 = new Choice(question, "choice2");
        Choice choice3 = new Choice(question, "choice3");

        Set<Choice> choiceSet = new HashSet<>(Arrays.asList(choice, choice1, choice2, choice3));

        question.setChoiceSet(choiceSet);

        activity1.setQuestion(question);
        activity1.setSection(section1);

        section.setActivitySet(new HashSet<>(Collections.singletonList(activity)));
        section1.setActivitySet(new HashSet<>(Collections.singletonList(activity1)));

        program.setSectionSet(new HashSet<>(Arrays.asList(section1, section)));

        Program savedPrgoram = programRepository.save(program);
        log.info("saved Program Id-----> " + savedPrgoram.getProgram_id() + "  Program description-----> " + savedPrgoram.getDescription()
                + "  Program name-----> " + savedPrgoram.getName());

        List<Section> sectionList = sectionRepository.findByProgram(savedPrgoram);
        sectionList.forEach(current ->
                log.info("saved Section Id-----> " + current.getSection_id() + "  Associated with program Id-----> " + current.getProgram().getProgram_id())
        );


    }
}