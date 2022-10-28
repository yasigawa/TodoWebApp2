package com.example.TodoWebApp;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TodoWebAppController {
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;
    
    @Autowired
    private TodoDetailsServiceImpl todoDetailsServiceImpl;

    @GetMapping
    public String index(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        
        String username = getUsername();
        
        // タスク一覧を取得する
        List<TodDetails> todolist = todoDetailsServiceImpl.select(username);
        model.addAttribute("todolist", todolist);
        System.out.println("index:" + model.toString());  // 
        return "index";
    }
    
	@GetMapping("/login")
    public String login () {
        return "login";
    }

    @GetMapping("/signup")
    public String newSignup(SignupForm signupForm) {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@Validated SignupForm signupForm, BindingResult result, Model model,
            HttpServletRequest request) {
    	// バリデーションエラー
        if (result.hasErrors()) {
            return "signup";
        }

        if (userDetailsServiceImpl.isExistUser(signupForm.getUsername())) {
            model.addAttribute("signupError", "ユーザーID " + signupForm.getUsername() + "は既に登録されています");
            return "signup";
        }

        try {
            userDetailsServiceImpl.register(signupForm.getUsername(), signupForm.getUsernamejp(), signupForm.getPassword(), "ROLE_USER");
        } catch (DataAccessException e) {
            model.addAttribute("signupError", "ユーザー登録に失敗しました");
            return "signup";
        }

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

		if (authentication instanceof AnonymousAuthenticationToken == false) {
			SecurityContextHolder.clearContext();
		}

        try {
            request.login(signupForm.getUsername(), signupForm.getPassword());
        } catch (ServletException e) {
            e.printStackTrace();
        }

        return "redirect:/";
    }

    @GetMapping("/addTodo")
    public String addTodo (Model model, TodoDetailForm todoDetailForm, boolean hasErr) {
    	String username = getUsername();
    	TodDetails tododetail = new TodoDetailsImpl("", username, "", "", "", "", "");
    	if (hasErr) {
    		// 入力エラー時
    		TodDetails d = new TodoDetailsImpl(
    	    		todoDetailForm.getId(),
    	    		username,
    	    		todoDetailForm.getTitle(),
    	    		todoDetailForm.getDetails(),
    	    		todoDetailForm.getTododate(),
    	    		todoDetailForm.getTodotime(),
    	    		""
    		);
    		tododetail = d;
    	}
        model.addAttribute("tododetail", tododetail);
        model.addAttribute("nextaction", "addTodo");
        return "addModtodo";
    }

    @PostMapping("/addTodo")
    public String addTodo(@Validated TodoDetailForm todoDetailForm, BindingResult result, Model model,
            HttpServletRequest request) {

    	String username = getUsername();
    	// バリデーションエラー
        if (result.hasErrors()) {
            return addTodo(model, todoDetailForm, true);
        }
        
        try {
        	todoDetailsServiceImpl.insert(
        			username,
    	    		todoDetailForm.getTitle(),
    	    		todoDetailForm.getDetails(),
    	    		todoDetailForm.getTododate(),
    	    		todoDetailForm.getTodotime()
    		);
        } catch (DataAccessException e) {
            model.addAttribute("TodoDetailError", "TODO登録に失敗しました");
            return addTodo(model, todoDetailForm, true);
        }
        
        // タスク一覧を取得する
        List<TodDetails> todolist = todoDetailsServiceImpl.select(username);
        model.addAttribute("todolist", todolist);
        model.addAttribute("todoAddModOkMessage", "TODOを追加しました。");
        
        return "index";
    }

    @GetMapping("/modTodo")
    public String modTodo(String id, Model model, TodoDetailForm todoDetailForm, boolean hasErr) {

    	String username = getUsername();

    	System.out.println("id:" + id);
    	TodDetails tododetail = todoDetailsServiceImpl.selectById(id);

    	if (hasErr) {
    		// 入力エラー時
    		TodDetails d = new TodoDetailsImpl(
    	    		todoDetailForm.getId(),
    	    		username,
    	    		todoDetailForm.getTitle(),
    	    		todoDetailForm.getDetails(),
    	    		todoDetailForm.getTododate(),
    	    		todoDetailForm.getTodotime(),
    	    		""
    		);
    		tododetail = d;
    	}
    	
        model.addAttribute("tododetail", tododetail);
        model.addAttribute("nextaction", "modTodo");
        return "addModtodo";
    }

    @PostMapping("/modTodo")
    public String modTodo(@Validated TodoDetailForm todoDetailForm, BindingResult result, Model model,
            HttpServletRequest request) {

    	String username = getUsername();

    	// バリデーションエラー
        if (result.hasErrors()) {
            return modTodo(todoDetailForm.getId(), model, todoDetailForm, true);
        }
        try {
        	todoDetailsServiceImpl.update(
    	    		todoDetailForm.getId(),
    	    		username,
    	    		todoDetailForm.getTitle(),
    	    		todoDetailForm.getDetails(),
    	    		todoDetailForm.getTododate(),
    	    		todoDetailForm.getTodotime()
    		);
        } catch (DataAccessException e) {
            model.addAttribute("TodoDetailError", "TODO更新に失敗しました");
            return modTodo(todoDetailForm.getId(), model, todoDetailForm, true);
        }
        
        // タスク一覧を取得する
        List<TodDetails> todolist = todoDetailsServiceImpl.select(username);
        model.addAttribute("todolist", todolist);
        model.addAttribute("todoAddModOkMessage", "TODOを修正しました。");
        
        return "index";
    }
    
    @GetMapping("/deleteTodo")
    public String deleteTodo(String id, Model model, TodoDetailForm todoDetailForm, boolean hasErr) {
    	String username = getUsername();

    	System.out.println("deleteTodo id:" + id + "username:" + username);
    	todoDetailsServiceImpl.delete(id, username);

        // タスク一覧を取得する
        List<TodDetails> todolist = todoDetailsServiceImpl.select(username);
        model.addAttribute("todolist", todolist);
        model.addAttribute("todoAddModOkMessage", "TODOを削除しました。");

        return "index";
    }

    @GetMapping("/toNotDone")
    public String toNotDone(String id, Model model, TodoDetailForm todoDetailForm, boolean hasErr) {
    	String username = getUsername();

    	todoDetailsServiceImpl.updateDone(id, username, "0");

        // タスク一覧を取得する
        List<TodDetails> todolist = todoDetailsServiceImpl.select(username);
        model.addAttribute("todolist", todolist);

        return "index";
    }

    @GetMapping("/toDone")
    public String toDone(String id, Model model, TodoDetailForm todoDetailForm, boolean hasErr) {
    	String username = getUsername();

    	todoDetailsServiceImpl.updateDone(id, username, "1");

        // タスク一覧を取得する
        List<TodDetails> todolist = todoDetailsServiceImpl.select(username);
        model.addAttribute("todolist", todolist);

        return "index";
    }

    private String getUsername() {
        // SecurityContextHolderからAuthenticationオブジェクトを取得
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        // Authenticationオブジェクトからユーザー情報を取得
        System.out.println(authentication.getName());  // ユーザー名を表示
        System.out.println(authentication.getAuthorities());  // 権限情報を表示

        UserDetailsImpl u = (UserDetailsImpl)authentication.getPrincipal();
        System.out.println("authentication username:" + u.getUsername());  // 
        
		return u.getUsername();
	}
}
