package me.mouamle.cs50_polls.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Projects {

    private static final Set<String> projects = new HashSet<>();
    private static final String[] names = {
            "Dolphin",
            "SAFR",
            "THE AUTISM PLATFORM",
            "FROM D TO G",
            "Companion",
            "Voluntary Skills Record",
            "محاميكم",
            "Electro Home",
            "أنوڤا",
            "مشاتل",
            "Environment Saver",
            "Solutions Safari",
            "بيت اكس",
            "شاغول",
            "Coding For Kids",
            "DesignChi",
            "Dr.Ai",
            "منصة مراكز التعليم المستمر",
            "CourseLink",
            "O2IRAQ"
    };

    static {
        projects.addAll(Arrays.asList(names));
    }

    public static String[] getNames() {
        return names;
    }

    public static boolean isProject(String project) {
        return projects.contains(project);
    }

}
