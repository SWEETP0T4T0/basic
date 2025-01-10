package com.beyond.basic.b1_hello.controller;

import com.beyond.basic.b1_hello.domain.Hello;
import com.beyond.basic.b1_hello.domain.StudentReqDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
    public String model_param(@RequestParam(value = "name") String inputName, Model model) {
//        Model 객체는 특정 데이터를 화면에 전달해주는 역할
//        modelName이라는 키값에 value를 세팅하면 modelName:값 형태로 화면에 전달
        model.addAttribute("modelName", inputName);
        return "helloworld2";
    }

    //    case7. 화면을 return해 주되, 객체를 화면에 동적으로 세팅
    @GetMapping("/model-param2")
    public String model_param2(@ModelAttribute Hello hello, Model model) {
        model.addAttribute("modelHello", hello);
        return "helloworld3";
    }

    //    case8. pathvariable 방식을 통해 사용자로부터 값을 받아 화면 return
//    형식 : /hello/model-path/hongildong
//    예시 : /author/detail/1  - 명확한 value를 찾을때
//    pathvariable 방식은 url을 통해 자원구조를 명확하게 표현할 때 사용.(좀더 restful한 방식)
    @GetMapping("/model-path/{inputName}")
    public String model_path(@PathVariable String inputName, Model model) {

        model.addAttribute("modelName", inputName);
        return "helloworld2";
    }

    //    사용자에게 name, email을 입력 할 수 있는 화면으 주는 메서드 정의
    @GetMapping("/form-view")
    public String formView() {
        return "form-view";
    }

    //    <post> case 1. form 데이터 형식의 post 요청 처리(텍스트만 있는 application/x-www~)
//    형식 : ?name=zxx&email=yyy 데이터가 http body에 들어옴
    @PostMapping("/form-view")
    @ResponseBody
    public String formPost1(Hello hello) {
        System.out.println(hello);
        return "ok";
    }

    //    case 2. form데이터 형식의 post 요청 처리 (file + text 형식)
    @GetMapping("/form-file-view")
    public String formfileview() {
        return "form-file-view";
    }

    @PostMapping("/form-file-view")
    @ResponseBody
//    java에서 파일을 처리하는 클래스 : MultipartFile
    public String formfPost2(Hello hello, @RequestParam(value = "photo") MultipartFile photo) {
        System.out.println(hello);
        System.out.println(photo.getOriginalFilename());
        return "ok";
    }

    //    case 3. javascript를 활용한 form 데이터 전송(text만)
    @GetMapping("/axios-form-view")
    public String axiosFormView() {
        return "axios-form-view";
    }

    @PostMapping("/axios-form-view")
    @ResponseBody
//    js를 통한 form형식도 마찬가지로 ?name-xxx&email=yyy이다, 따라서 데이터 바인딩으로 처리 할 수 있다.
    public String axiofFormPost(Hello hello) {
        System.out.println(hello);
        return "ok";
    }

    //    case 4. js를 활용한 form데이터 전송(text+file)
    @GetMapping("/axios-form-file-view")
    public String axiosFormFileView() {
        return "axios-form-file-view";
    }


    @PostMapping("/axios-form-file-view")
    @ResponseBody
    public String axiofFormPost2(Hello hello, @RequestParam(value = "photo") MultipartFile photo) {
        System.out.println(hello);
        return "ok";
    }

    //    case 5. js를 활용한 form데이터 전송(text + 멀티 file)
    @GetMapping("/axios-form-multi-file-view")
    public String axiosFormMultyFileView() {
        return "axios-form-multi-file-view";
    }


    @PostMapping("/axios-form-multi-file-view")
    @ResponseBody
    public String axiofFormMultyPost2(Hello hello, @RequestParam(value = "photos") List<MultipartFile> photos) {
        System.out.println(hello);
        for (int i = 0; i < photos.size(); i++) {
            System.out.println(photos.get(i).getOriginalFilename());
        }
        return "ok";
    }

    //    case 6. js를 활용한 json데이터 전송
//    형식 : {name:"hong", email:"hong@naver.com"}
    @GetMapping("/axios-json-view")
    public String axiosJsonView() {
        return "axios-json-view";
    }

    @PostMapping("/axios-json-view")
    @ResponseBody
//    json으로 들어오면 무적권 RequestBody 붙여야함
    public String axiosJsonViewPost(@RequestBody Hello hello) {
        System.out.println(hello);
        return "ok";
    }

    //    case 7. 중첩된 json 데이터 처리
//    예시데이터(student 객체) : {name:'hong', email:'hong@naver.com', scores:'[{math:60}, {music:70}, {english:60}]}
    @GetMapping("/axios-nested-json-view")
    public String axiosNestedJsonView() {
        return "axios-nested-json-view";
    }

    @PostMapping("/axios-nested-json-view")
    @ResponseBody
    public String axiosNestedJsonViewPost(@RequestBody StudentReqDto studentReqDto) {
        System.out.println(studentReqDto.getName());
        System.out.println(studentReqDto.getEmail());

        for (StudentReqDto.Score score : studentReqDto.getScores()) {
            System.out.println(score.getSubject());
            System.out.println(score.getPoint());
        }
        return "ok";
    }

    //    case 8. json과 file처리
//    file처리는 기본적으로 form형식을 통해 처리
//    그래서, json과 file을 동시에 처리하려면 form 형식 안에 json과 file을 넣어 처리한다
    @GetMapping("/axios-json-file-view")
    public String axiosJsonFileView() {
        return "/axios-json-file-view";
    }

//    데이터 형식 : ?hello={name:'hong',email:'hong@naver.com'}&photo=이미지

//    RequestParam으로 받을 때
//    @PostMapping("/axios-json-file-view")
//    @ResponseBody
//    public String axiosJsonFileViewPost(
//            @RequestParam(value = "hello") String helloString,
//            @RequestParam(value = "photo") MultipartFile photo
//    ) throws JsonProcessingException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        Hello h1 = objectMapper.readValue(helloString, Hello.class);
//        System.out.println(h1);
//        System.out.println(photo.getOriginalFilename());
//        return "OK";
//    }

    //    RequestPart로 받을 때
    @PostMapping("/axios-json-file-view")
    @ResponseBody
    public String axiosJsonFileViewPost(
//            RequestPart는 json과 file을 함께 처리할 때 많이 사용
            @RequestPart("hello") Hello hello,
            @RequestPart("photo") MultipartFile photo
    ) throws JsonProcessingException {
        System.out.println(hello);
        System.out.println(photo.getOriginalFilename());
        return "OK";
    }
}



