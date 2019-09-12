package config.Controllers;


import config.DTO.AnswerDTO;
import config.DTO.Question;
import config.DTO.TestDTO;
import config.DTO.UserDTO;
import config.Entities.Test;
import config.Entities.User;
import config.Entities.UserToTest;
import config.repositories.RoleRepository;
import config.repositories.TestRepository;
import config.repositories.UserRepository;
import config.repositories.UserTOTestRepository;
import config.service.UniversalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RequestMapping("/")
@RestController
public class MainController {

    @Value("${error.message}")
    private String addingPersonError;

    @Value("${success.message}")
    private String addingPersonSuccess;

    @Value("${error.adding}")
    private String UserExisted;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    TestRepository testRepository;

    @Autowired
    UserTOTestRepository userTOTestRepository;

    @Autowired
    UniversalService universalService;


    @GetMapping("/all")
    public ModelAndView hello(Model model)
    {
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("message", "sometest");

        return mav;
    }

    @RequestMapping(value = "/addPerson", method = RequestMethod.GET)
    public ModelAndView addUserGet(Model m)
    {
        ModelAndView mav = new ModelAndView("registration");
        UserDTO userDTO = new UserDTO();
        mav.addObject("userDTO",userDTO);
        return mav;
    }

    @RequestMapping(value = "/addPerson", method = RequestMethod.POST)
    public ModelAndView addUser(Model m, @ModelAttribute UserDTO userDTO)
    {
        ModelAndView mav = new ModelAndView("registration");
        if(!userDTO.getPassword1().equals(userDTO.getPassword2()))
        {
            mav.addObject("message", addingPersonError);
            return mav;
        }
        if(universalService.findUserByUsername(userDTO.getUsername()) != null)
        {
            mav.addObject("message", UserExisted);
            return mav;
        }
        if(userDTO.getPassword1().equals(userDTO.getPassword2()))
        {
            User obj = new User();
            obj.setUsername(userDTO.getUsername());
            obj.setPassword(userDTO.getPassword1());
            //
            roleRepository.getRoleByName("ROLE_USER").getUsers().add(obj);
            //obj.getRoles().add(roleRepository.getRoleByName("ROLE_USER"));

            //roleRepository.getRoleByName("ROLE_USER").getUsers().add(obj);
            userRepository.save(obj);
            mav.addObject("message", addingPersonSuccess);
        }
        return mav;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/secured/all")
    public String securedhello()
    {
        return "secured hello";
    }


    @GetMapping("/unsecured/all")
    public String unsecuredhello()
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            String currentUserName = auth.getName();
            return currentUserName;
        }
        return "unsecured hello";
    }

    @RequestMapping(value = "/tests")
    public ModelAndView printTests(Model m)
    {
        ModelAndView mav = new ModelAndView("tests.html");
        TestDTO testDTO = new TestDTO(testRepository.findAll());
        mav.addObject("questions",testDTO.getTestItself().keySet());
        return  mav;
    }

    @RequestMapping(value = "/tests/", method = RequestMethod.GET)
    public ModelAndView getTest(@RequestParam Map<String,String> allParams, Model m)
    {
        System.out.println();
        ModelAndView mav = new ModelAndView("testPassing.html");
        TestDTO testDTO = new TestDTO(testRepository.findAll());//make global
        AnswerDTO answerDTO = new AnswerDTO();
        mav.addObject("topic",allParams.get("name"));
        mav.addObject("Test",testDTO.getTestItself().get(allParams.get("name")));
        mav.addObject("output",answerDTO);
        return  mav;
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @RequestMapping(value = "/processTest",method = RequestMethod.POST)
    public String CountTestResult(@RequestParam Map<String,String> allParams
            ,@ModelAttribute(value = "output") AnswerDTO answerDTO)
    {
        int mark = 0;
        for (String s: answerDTO.getResult()
             ) {
            System.out.println(s);
        }
        TestDTO testDTO = new TestDTO(testRepository.findAll());
        LinkedList<Question> out =  testDTO.getTestItself().get(allParams.get("name"));

        for (int i = 0; i < out.size(); i++)
        {
            for (int j = 0; j < answerDTO.getResult().size(); j++)
            {
                if (out.get(i).getOptions()[out.get(i).getNumOfRightAnswer()]
                        .equals(answerDTO.getResult().get(j))

                )
                {
                    mark +=2;
                }
            }
        }

        mark -= answerDTO.getResult().size();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User u = userRepository.findByUsername(auth.getName());

        Test t = testRepository.findAll().get(testDTO.getTestID().get(allParams.get("name")));


        UserToTest utt = new UserToTest();
        utt.setMark(mark);
        utt.setTest_id(t.getId());
        utt.setUser_id((int)u.getId());
        userTOTestRepository.save(utt);


        return Integer.toString(mark);
    }
}
