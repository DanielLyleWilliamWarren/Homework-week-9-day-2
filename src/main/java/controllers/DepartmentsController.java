package controllers;

import db.DBHelper;
import db.Seeds;
import models.Department;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.get;

public class DepartmentsController {
    public DepartmentsController() {
        this.setupEndPoints();
    }

    private void setupEndPoints() {


        //VIEW

        get("/departments", (req, res) -> {
            Map<String, Object> model = new HashMap();
            model.put("template", "templates/departments/index.vtl");

            List<Department> departments = DBHelper.getAll(Department.class);
            model.put("departments", departments);

            return new ModelAndView(model, "templates/layout.vtl");

        }, new VelocityTemplateEngine());
    }

}
