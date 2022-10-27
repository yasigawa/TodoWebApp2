package com.example.TodoWebApp;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TodoWebAppControllerTesst {

	private MockMvc mockMvc;

	@Autowired
	TodoWebAppController target;

	@BeforeEach
	public void setup() {
	    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
	    viewResolver.setPrefix("/templates");
	    viewResolver.setSuffix(".html");

	    mockMvc = MockMvcBuilders.standaloneSetup(target)
        		.setViewResolvers(viewResolver)
        		.build();

	}

	// indexのテスト
	@Test
	@WithUserDetails("guest")
    public void getIndexTest() throws Exception {

		mockMvc.perform(get("/"))
			.andExpect(status().isOk())
			.andExpect(view().name("index"));
    }

	// loginのテスト
	@Test
    public void getLoginTest() throws Exception {

		mockMvc.perform(get("/login"))
			.andExpect(status().isOk())
			.andExpect(view().name("login"));
    }

	// newSignupのテスト
	@Test
    public void getSignupTest() throws Exception {

		mockMvc.perform(get("/signup"))
			.andExpect(status().isOk())
			.andExpect(view().name("signup"));
    }

	// signupのテスト１
	@Test
    public void postSignupTest1() throws Exception {

		// バリデーションエラーの場合
		SignupForm form = new SignupForm();
	    form.setUserid("");
	    form.setUsername("guesttest");
	    form.setPassword("111111");

	    MockHttpServletRequestBuilder request = (post("/signup")).flashAttr("signupForm", form);

	    mockMvc.perform(request)
	    	.andExpect(status().isOk())
	        .andExpect(view().name("signup"));
	}

	// signupのテスト２
	@Test
    public void postSignupTest2() throws Exception {

		// 登録されていますエラー
		SignupForm form = new SignupForm();
	    form.setUserid("guest");
	    form.setUsername("guesttest");
	    form.setPassword("111111");

	    MockHttpServletRequestBuilder request = (post("/signup")).flashAttr("signupForm", form);

	    mockMvc.perform(request)
	    	.andExpect(status().isOk())
	        .andExpect(view().name("signup"));
	}

	// addTodoのテスト
	@Test
	@WithUserDetails("guest")
    public void getAddTodoTest() throws Exception {

		mockMvc.perform(get("/addTodo"))
			.andExpect(status().isOk())
			.andExpect(view().name("addModtodo"));
    }

	// Post　addTodoのテスト１
	@Test
	@WithUserDetails("guest")
    public void postAddTodoTest() throws Exception {
		TodoDetailForm form = new TodoDetailForm();
	    form.setUserid("guest");
	    form.setTitle("111111");

	    mockMvc.perform(post("/addTodo").flashAttr("todoDetailForm", form))
			.andExpect(status().isOk())
			.andExpect(view().name("index"));
    }

	// Post　addTodoのテスト２
	@Test
	@WithUserDetails("guest")
    public void postAddTodoTest2() throws Exception {
		
		// バリデーションエラー
		TodoDetailForm form = new TodoDetailForm();
	    form.setUserid("guest");
	    form.setTitle("");

	    mockMvc.perform(post("/addTodo").flashAttr("todoDetailForm", form))
			.andExpect(status().isOk())
			.andExpect(view().name("addModtodo"));
    }

	// Get　modTodoのテスト
	@Test
	@WithUserDetails("guest")
    public void getModTodoTest() throws Exception {

		mockMvc.perform(get("/modTodo").param("id", "68"))
			.andExpect(status().isOk())
			.andExpect(view().name("addModtodo"));
    }

	// Post　modTodoのテスト
	@Test
	@WithUserDetails("guest")
    public void postModTodoTest() throws Exception {
		TodoDetailForm form = new TodoDetailForm();
	    form.setUserid("guest");
	    form.setTitle("111111");

		mockMvc.perform(post("/modTodo").flashAttr("todoDetailForm", form))
			.andExpect(status().isOk())
			.andExpect(view().name("index"));
    }
    
	// Get　deleteTodoのテスト
	@Test
	@WithUserDetails("guest")
    public void getDeleteTodoTest() throws Exception {
		TodoDetailForm form = new TodoDetailForm();
	    form.setUserid("guest");
	    form.setTitle("111111");

		mockMvc.perform(get("/deleteTodo").flashAttr("todoDetailForm", form))
			.andExpect(status().isOk())
			.andExpect(view().name("index"));
    }

	// Get　toNotDoneのテスト
	@Test
	@WithUserDetails("guest")
    public void gettoNotDoneTest() throws Exception {

		mockMvc.perform(get("/toNotDone").param("id", "67"))
			.andExpect(status().isOk())
			.andExpect(view().name("index"));
    }

	// Get　toDoneのテスト
	@Test
	@WithUserDetails("guest")
    public void gettoDoneTest() throws Exception {

		mockMvc.perform(get("/toDone").param("id", "67"))
			.andExpect(status().isOk())
			.andExpect(view().name("index"));
    }
}
