package by.mordas.project;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/FirstServlet")
public class FirstServlet extends HttpServlet {
    @Override
    public void destroy() {
        super.destroy();

    }

    @Override
    public void init() throws ServletException {
        super.init();



    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DBConnect.getInstance().writeInTable(req.getParameter("insert"));
        resp.setContentType("text/html; charset=utf-8");
        resp.getWriter().print(DBConnect.getInstance().readFromTable().toUpperCase());
        DBConnect.getInstance().deleteAllFromTable();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.getWriter().print("This is " + this.getClass().getName()
                + ", using the GET method");
    }


}
