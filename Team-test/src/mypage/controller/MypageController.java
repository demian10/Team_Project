package mypage.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.db.SessionCheck;
import mypage.action.Action;
import mypage.action.LogoutAction;
import mypage.action.MyPageAction;
import mypage.action.MyPageListAction;
import mypage.action.PickListAction;
import mypage.action.UserDeleteProAction;
import mypage.action.UserUpdateFormAction;
import mypage.action.UserUpdateProAction;
import mypage.vo.ActionForward;

@WebServlet("*.mp")
public class MypageController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doProcess(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doProcess(request, response);
    }

    protected void doProcess(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();
        String command = requestURI.substring(contextPath.length());

        System.out.println(command);

        Action action = null;
        ActionForward forward = null;

        
        // 세션이 필요한 행동
        if(SessionCheck.SessionCheck(request)) {
        	if (command.equals("/myPage.mp")) {
                action = new MyPageAction();
                try {
                    forward = action.execute(request, response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (command.equals("/BoardUpdateForm.mp")) {
                forward = new ActionForward();
                forward.setPath("./mypage/board_update.jsp");
            } else if (command.equals("/PickList.mp")) {
                action = new PickListAction();
                try {
                    forward = action.execute(request, response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (command.equals("/Logout.mp")) {
            	action = new LogoutAction();
            	try {
                    forward = action.execute(request, response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (command.equals("/UserUpdateForm.mp")) {
            	
            	action = new UserUpdateFormAction();
                try {
                    forward = action.execute(request, response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (command.equals("/UserDeleteForm.mp")) {
            	forward = new ActionForward();
            	forward.setPath("./mypage/user_delete.jsp");
            } else if (command.equals("/IdCheck.mp")) {
            	forward = new ActionForward();
            	forward.setPath("./mypage/user_update_passCheck.jsp");
            } else if (command.equals("/IdCheckPro.mp")) {
            	action = new UserUpdateFormAction();
            	try {
                    forward = action.execute(request, response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (command.equals("/UserUpdatePro.mp")) {
            	action = new UserUpdateProAction();
            	try {
                    forward = action.execute(request, response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (command.equals("/UserDeletePro.mp")) {
            	action = new UserDeleteProAction();
            	try {
                    forward = action.execute(request, response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
			} else if (command.equals("/boardUpdateListForm.mp")) {
				action = new MyPageListAction();

				try {
					forward = action.execute(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} 
        } else {
            forward = new ActionForward();
            request.setAttribute("msg", "로그인이 필요합니다");
            forward.setPath("/member/msg.jsp");
        }
        
        
        
        


//        else if (command.equals("/businessJoinForm.mp")) {
//            forward = new ActionForward();
//            forward.setPath("./businessChange.jsp");
//        } else if (command.equals("/businessJoinPro.mp")) {
//
//            action = new BusinessJoinProAction();
//
//            // Action 클래스의 execute() 메서드를 호출하여 request(요청), response(응답) 객체를 전달한 뒤
//            // 요청된 작업을 처리한 후 ActionForward 객체를 리턴 => throws 키워드를 통해 예외처리가 위임되어있음
//            try {
//                forward = action.execute(request, response); // BoardWriteProAction 객체의 execute() 메서드 호출
//                // execute() 메서드 실행 후 ActionForward 객체를 리턴받아 포워딩
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }

        if (forward != null) {
            if (forward.isRedirect()) {
                response.sendRedirect(forward.getPath());
                System.out.println("redirect 방식으로 포워딩 : " + forward.getPath());
            } else {
                RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath());
                dispatcher.forward(request, response);
                System.out.println("dispatch 방식으로 포워딩 : " + forward.getPath());
            }
        }
    }
}
