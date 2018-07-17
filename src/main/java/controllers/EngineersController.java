package controllers;

import db.DBHelper;
import models.Department;
import models.Engineer;
import models.Manager;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.post;

public class EngineersController {

    public EngineersController() {
        this.setupEndPoints();
    }
    private void setupEndPoints(){
        // READ
        get("/engineers", (res,req) ->{
            Map<String, Object> model = new HashMap<>();
            model.put("template", "templates/engineers/index.vtl");

            List<Engineer> engineers = DBHelper.getAll(Engineer.class);
            model.put("engineers", engineers);
            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());


        //CREATE GET
        get("/engineers/new", (req, res) -> {
            HashMap<String, Object> model = new HashMap();

            List<Department> departments = DBHelper.getAll(Department.class);
            model.put("departments", departments);

            model.put("template", "templates/engineers/create.vtl");
            return new ModelAndView(model, "templates/layout.vtl");

        }, new VelocityTemplateEngine());

        //CREATE POST
        post("/engineers", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            String firstName = req.queryParams("first-name");
            String lastName = req.queryParams("last-name");
            int salary = Integer.parseInt(req.queryParams("salary"));
            int departmentId = Integer.parseInt(req.queryParams("department"));
            Department department = DBHelper.find(departmentId, Department.class);

            Engineer engineer = new Engineer(firstName, lastName, salary, department);
            DBHelper.save(engineer);

            res.redirect("/engineers");
            return null;

        }, new VelocityTemplateEngine());

        // DELETE
        post("/engineers/:id/delete", (req, res) -> {
            int id = Integer.parseInt(req.params(":id"));
            Engineer engineer = DBHelper.find(id, Engineer.class);
            DBHelper.delete(engineer);
            res.redirect("/engineers");
            return null;
        }, new VelocityTemplateEngine());

    }
}