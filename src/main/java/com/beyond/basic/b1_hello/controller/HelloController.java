package com.beyond.basic.b1_hello.controller;

import com.beyond.basic.b1_hello.domain.Hello;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

//Component 어노테이션을 통헤 별도의 객체를 생성할 필요가 없는, 싱글톤 객체를 생성
//Controller 어노테이션을 통해 쉽게 사용자의 http 요청을 처리하고, http 응답을 줄 수 있음.
@Controller
//클래스 차원에 url 매핑시에는 RequestMapping사용
@RequestMapping("/hello")
public class HelloController {

    //    case1. 서버가 사용자에게 단순 string 데이터 return(get요청) - @ResponseBody가 존재 할 때
//    case2. 서버가 사용자에게 화면을 return(get요청) - @ResponseBody가 존재하지 않을 때
    @GetMapping("")
//    @ResponseBody
//    @ResponseBody가 없고, return타입이 String일 경우에 서버는 templates폴더 밑에 helloworld.html 화면을 찾아 리턴한다.
    public String helloworld() {
        return "helloworld";
    }

    //    case3.서버가 사용자에게 json형식의 데이터를 return(get요청)
    @GetMapping("/json")
//    메서드 차원에서도 RequestMapping 사용가능
//    @RequestMapping(value = "/json", method = RequestMethod.GET)
    @ResponseBody
    public Hello helloJson() throws JsonProcessingException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        Hello h1 = new Hello("hongildong","hong@naver.com");
//        String value = objectMapper.writeValueAsString(h1);
//        직접 json으로 직렬화 할 필요 없이, return타입을 클래스로 지정시에 자동으로 직렬화
        Hello h1 = new Hello("hongildong", "hong@naver.com");

        return h1;
    }

    //    case4. 사용자가 json 데이터를 요청할 때, parameter형식으로 특정객체 요청(get요청)
//    parameter형식 : /hello/param1?name=hongildong
    @GetMapping("/param1")
    @ResponseBody
    public Hello param1(@RequestParam(value = "name") String inputName) {
        Hello h1 = new Hello(inputName, "test@naver.com");

        return h1;
    }

    //    case4-2. 사용자가 json 데이터를 요청할 때, parameter형식으로 특정객체 요청(get요청)
//    parameter 2개 이상 형식 : /hello/param1?name=hongildong&email=hongildong@naver.com
    @GetMapping("/param2")
    @ResponseBody
    public Hello param2(@RequestParam(value = "name") String inputName, @RequestParam(value = "email") String inputEmail) {
        Hello h1 = new Hello(inputName, inputEmail);

        return h1;
    }

    //    case5. parameter가 많아질 경우 데이터 바인딩을 통해 input값 처리
    @GetMapping("/param3")
    @ResponseBody
//    각 parameter의 값이 Hello 클래스의 각 변수에 Mapping이 된다.
//    public Hello param3(Hello hello){
//        return hello;

    public Hello param3(@ModelAttribute Hello hello) {
        return hello;
    }

//    case6. 화면을 리턴 해 주되, 특정 변수값을 동적으로 세팅
    @GetMapping("/model-param")
    public String model_param(@RequestParam(value = "name")String inputName, Model model){
//        Model 객체는 특정 데이터를 화면에 전달해주는 역할
//        modelName이라는 키값에 value를 세팅하면 modelName:값 형태로 화면에 전달
        model.addAttribute("modelName", inputName);
        return "helloworld2";
    }
}



