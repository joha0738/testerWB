package com.example.tester.controllers;


import com.example.tester.models.*;
import com.example.tester.repos.AnswersRepository;
import com.example.tester.repos.QuestionsRepository;
import com.example.tester.repos.ResultsRepository;
import com.example.tester.repos.TestsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.*;



@Controller
public class TestController {

    @Autowired
    private TestsRepository testsRepository;

    @Autowired
    private QuestionsRepository questionsRepository;

    @Autowired
    private AnswersRepository answersRepository;

    @Autowired
    private ResultsRepository resultsRepository;

    @GetMapping("/tests")
    public String testpage(Model model){
        Iterable<Tests> tests = testsRepository.findAll();
        model.addAttribute("test",tests);

        model.addAttribute("cl",0);

        return "test-page";
    }

        @GetMapping("/test/{id}/{cl}")
        public String test(@PathVariable(value = "id") long id,@PathVariable(value = "cl") int cl, Model model) {

            List<Questions> quest = questionsRepository.findBytestsId(id);

            int number = quest.size();
            model.addAttribute("counter",number);
            model.addAttribute("count",cl+1);

            if (cl < quest.size()){
                String questTitle = quest.get(cl).getQuestion();
                Long questId = quest.get(cl).getId();
              List<Answers> answ = answersRepository.findByQuestionsId(questId);
            model.addAttribute("answer",answ);
            model.addAttribute("quest", questTitle);
            model.addAttribute("id", id);
            if(cl+1==quest.size())
                model.addAttribute("continue","Закончить");
            else
                model.addAttribute("continue","Дальше");

            cl++;
            model.addAttribute("cl", cl);

            if(quest.get(cl-1).isMulti()){
                return "test-multi";
            }else {
                return "test";
            }

        } else {
                return "message";
            }
        }

        @PostMapping("/test/{id}/{cl}")
        public String postTest(@RequestParam String answer, @PathVariable(value = "id") long id, @RequestParam String quest, @AuthenticationPrincipal User user, @PathVariable(value = "cl") int cl){

        Results results;
        if(resultsRepository.findByUserAndQuest(user.getUsername(),quest)==null){
            results= new Results();
        } else {
            results=resultsRepository.findByUserAndQuest(user.getUsername(), quest);
        }
            results.setUser(user.getUsername());
            results.setTest(questionsRepository.findByQuestion(quest).getTests().getTitle());
            results.setQuest(quest);
            results.setAnswer(answer);
            results.setNumber(cl);
            resultsRepository.save(results);

        return "redirect:/test/"+id+"/"+cl;
        }

    @GetMapping("/test/add")
    public String testAdd(){
        return "test-create";
    }

    @PostMapping("/test/add")
    public String postTestAdd(@RequestParam String test_name,Model model) {

        Tests test = new Tests();
        test.setTitle(test_name);
        testsRepository.save(test);
        model.addAttribute("test",test);
        model.addAttribute("count","1");

        return "test-create-f";
    }


    @RequestMapping(value = "/test/add/question", method = RequestMethod.POST, params = "next")
    public String postTestAddQuest(@RequestParam String count,@RequestParam(defaultValue = "false") boolean multi,@RequestParam String testTitle,@RequestParam String question,@RequestParam String answer1,@RequestParam String answer2,@RequestParam String answer3,@RequestParam String answer4,Model model){

        int empty = 0;
        if(!answer1.equals(""))
            empty++;
        if(!answer2.equals(""))
            empty++;
        if(!answer3.equals(""))
            empty++;
        if(!answer4.equals(""))
            empty++;

        if (empty>=2) {

            Tests test = testsRepository.findByTitle(testTitle);

            Questions quest;
            if (questionsRepository.findByQuestion(question) == null) {
                quest = new Questions();
                quest.setQuestion(question);
                quest.setTests(test);
                quest.setMulti(multi);
                questionsRepository.save(quest);
            } else {
                quest = questionsRepository.findByQuestion(question);
            }

            Answers ans1 = new Answers();
            Answers ans2 = new Answers();
            Answers ans3 = new Answers();
            Answers ans4 = new Answers();

            ans1.setAnswer(answer1);
            ans2.setAnswer(answer2);
            ans3.setAnswer(answer3);
            ans4.setAnswer(answer4);

            ans1.setQuestions(quest);
            ans2.setQuestions(quest);
            ans3.setQuestions(quest);
            ans4.setQuestions(quest);

            if (!answer1.equals("")) {
                answersRepository.save(ans1);
            }
            if (!answer2.equals("")) {
                answersRepository.save(ans2);
            }
            if (!answer3.equals("")) {
                answersRepository.save(ans3);
            }
            if (!answer4.equals("")) {
                answersRepository.save(ans4);
            }
            model.addAttribute("count", Integer.parseInt(count)+1);
            model.addAttribute("test", test);
            return "test-create-f";
        }
        else {
            model.addAttribute("error","Введите как минимум 2 варинта ответа!");
            model.addAttribute("count", Integer.parseInt(count)+1);
            model.addAttribute("test", testsRepository.findByTitle(testTitle));
            model.addAttribute("question",question);
            model.addAttribute("multi",multi);
            return "test-create-f";
        }
    }

    @RequestMapping(value = "/test/add/question", method = RequestMethod.POST, params = "add")
    public String postTestAddAnswer(@RequestParam String count,@RequestParam String testTitle,@RequestParam String question,@RequestParam(defaultValue = "false") boolean multi,@RequestParam String answer1,@RequestParam String answer2,@RequestParam String answer3,@RequestParam String answer4,Model model){

        Tests test = testsRepository.findByTitle(testTitle);

        Questions quest;
        if(questionsRepository.findByQuestion(question)==null) {
            quest = new Questions();
            quest.setQuestion(question);
            quest.setTests(test);
            quest.setMulti(multi);
            questionsRepository.save(quest);
        } else {
            quest=questionsRepository.findByQuestion(question);
        }


        Answers ans1 = new Answers();
        Answers ans2 = new Answers();
        Answers ans3 = new Answers();
        Answers ans4 = new Answers();

        ans1.setAnswer(answer1);
        ans2.setAnswer(answer2);
        ans3.setAnswer(answer3);
        ans4.setAnswer(answer4);

        ans1.setQuestions(quest);
        ans2.setQuestions(quest);
        ans3.setQuestions(quest);
        ans4.setQuestions(quest);

        if (!answer1.equals("")) {
            answersRepository.save(ans1);
        }
        if (!answer2.equals("")) {
            answersRepository.save(ans2);
        }
        if (!answer3.equals("")) {
            answersRepository.save(ans3);
        }
        if (!answer4.equals("")) {
            answersRepository.save(ans4);
        }

        model.addAttribute("readonly",true);
        model.addAttribute("multi",multi);
        model.addAttribute("count",Integer.parseInt(count));
        model.addAttribute("test",test);
        model.addAttribute("question",question);
        return "test-create-f";
    }

    @GetMapping("/tests/{id}/users")
    public String testsResults(@PathVariable(value = "id") long id,Model model){
       List<Results> results = resultsRepository.findByTest(testsRepository.findById(id).get().getTitle());
       List<String> users = new ArrayList<>();
       results.forEach(result-> users.add(result.getUser()));

        model.addAttribute("users",users.stream().distinct().toList());
        Long ids = id;
        model.addAttribute("id",ids);
        return "test-users";
    }

    @GetMapping("/tests/{id}/{user}")
    public String userResults(@PathVariable(value = "id") long id,@PathVariable(value = "user") String user,Model model){
        String test = testsRepository.findById(id).get().getTitle();
        List<Results> res = resultsRepository.findByUserAndTest(user,test);
        List<Integer> dev = new ArrayList<>();
        res.forEach(x-> dev.add(dev.size() + 1));

        model.addAttribute("user",user);
        model.addAttribute("counter",dev);
        model.addAttribute("result",res);
        model.addAttribute("test",test);
        return "about";
    }

    @GetMapping("/tests/{id}/edit")
    public String editTest(@PathVariable( value = "id") long id,Model model){
        Tests test = testsRepository.findById(id).orElseThrow(null);
        List<Questions> questions = questionsRepository.findBytestsId(id);
        Map<Questions,List<Answers>> AnsQuest = new LinkedHashMap<>();
        for(Questions asd : questions) {
            List<Answers> answers = answersRepository.findByQuestionsId(asd.getId());
            AnsQuest.put(asd,answers);
        }
        model.addAttribute("test",test);
        model.addAttribute("answers",AnsQuest);
        return "test-edit";
    }

    @GetMapping("/tests/{test.id}/edit/{all.key.id}")
    public String editQuest(@PathVariable(value = "test.id") Tests test,@PathVariable (value = "all.key.id") long id,Model model){

        Questions quest = questionsRepository.getById(id);
        List<Answers> answers = answersRepository.findByQuestionsId(quest.getId());

        model.addAttribute("test",test);
        model.addAttribute("quest",quest);
        model.addAttribute("answers",answers);

        return "test-edit-question";
    }

    @RequestMapping(value = "/tests/{id}/edit/{all.key.id}", method = RequestMethod.POST, params="save")
    public String saveEditTest(@PathVariable(value = "all.key.id") long id,@RequestParam(value = "question") String question,@RequestParam(value = "answers") List<String> answers){
        Questions question1 = questionsRepository.findById(id).orElseThrow();
        question1.setQuestion(question);
        questionsRepository.save(question1);

        List<Answers> answers1 = answersRepository.findByQuestionsId(id);
        for (int i = 0; i < answers.size(); i++) {
            answers1.get(i).setAnswer(answers.get(i));
            answersRepository.save(answers1.get(i));
        }

        return "redirect:/tests/{id}/edit";
    }

    @RequestMapping(value = "/tests/{id}/edit/{all.key.id}", method = RequestMethod.POST, params="remove")
    public String removeQuestTest(@PathVariable(value = "all.key.id") long id){
        Questions question = questionsRepository.findById(id).orElseThrow();
        List<Answers> answers = answersRepository.findByQuestionsId(id);
        for (Answers ans: answers){
            answersRepository.delete(ans);
        }
        questionsRepository.delete(question);
        return "redirect:/tests/{id}/edit";
    }

    @RequestMapping(value = "/tests/{id}/edit", method = RequestMethod.POST, params="savetitle")
    public String saveTitle(@PathVariable(value = "id") Tests test,@RequestParam(value = "testtitle") String title){
        test.setTitle(title);
        testsRepository.save(test);
        return "redirect:/tests/{id}/edit";
    }

    @RequestMapping(value = "/tests/{id}/edit", method = RequestMethod.POST, params="remove")
    public String removeTest(@PathVariable(value = "id") Tests test){
        List<Questions> question = questionsRepository.findBytestsId(test.getId());
        for(Questions quest : question){
           List<Answers> ans = answersRepository.findByQuestionsId(quest.getId());
            for(Answers ans1 : ans){
                answersRepository.delete(ans1);
            }
            questionsRepository.delete(quest);
        }
        testsRepository.delete(test);
        return "redirect:/tests";
    }

    }