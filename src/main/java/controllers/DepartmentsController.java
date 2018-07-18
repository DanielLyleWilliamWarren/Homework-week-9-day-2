package controllers;

import db.DBHelper;
import db.Seeds;
import models.Department;
import models.Engineer;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.post;

public class DepartmentsController {
    public DepartmentsController() {
        this.setupEndPoints();
    }

    private void setupEndPoints() {


        //Read

        get("/departments", (req, res) -> {
            Map<String, Object> model = new HashMap();
            model.put("template", "templates/departments/index.vtl");

            List<Department> departments = DBHelper.getAll(Department.class);
            model.put("departments", departments);

            return new ModelAndView(model, "templates/layout.vtl");

        }, new VelocityTemplateEngine());

        //CREATE GET

        get("/departments/new", (req, res) -> {
            HashMap<String, Object> model = new HashMap();

            model.put("template", "templates/departments/create.vtl");
            return new ModelAndView(model, "templates/layout.vtl");

        }, new VelocityTemplateEngine());

        //CREATE POST

        post("/departments", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            String title = req.queryParams("title");
            Department department = new Department(title);
            DBHelper.save(department);

            res.redirect("/departments");
            return null;

        }, new VelocityTemplateEngine());

        // DELETE
        post("/departments/:id/delete", (req, res) -> {
            int id = Integer.parseInt(req.params(":id"));
            Department department = DBHelper.find(id, Department.class);
            DBHelper.delete(department);
            res.redirect("/departments");
            return null;
        }, new VelocityTemplateEngine());


    }
}



