package com.tetsoft.typego.data.achievement;

import com.tetsoft.typego.testing.TypingResult;
import com.tetsoft.typego.data.account.User;
import java.util.ArrayList;
import java.util.Date;

public class Achievement {
    private int id;
    private final int assignedImageId;
    private final String name;
    private final String description;
    private Date completionDate;
    private final boolean progressAttached;
    private final ArrayList<Requirement> requirements; // Progress will be shown based of it's very first value.

   public Achievement(int id,
                      String name,
                      String description,
                      int assignedImageId,
                      boolean progressAttached,
                      ArrayList<Requirement> requirements
   ) {
       this.id = id;
       this.name = name;
       this.description = description;
       this.assignedImageId = assignedImageId;
       this.progressAttached = progressAttached;
       this.requirements = requirements;
   }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Date getCompletionDate() {
       return completionDate;
    }

    public int getId() {
       return id;
    }

    public void setId(int id) {
       this.id = id;
    }

    public int getAssignedImageId() {
        return assignedImageId;
    }

    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

    public boolean isProgressAttached() {
        return progressAttached;
    }

    public ArrayList<Requirement> getRequirements() {
        return requirements;
    }

    public boolean isCompleted() {
       return completionDate != null;
    }

    // Returns true if user completed the achievement.
    public boolean requirementsAreComplete(User user, TypingResult result) {
       for (Requirement requirement: requirements)
           // if any of the requirements isn't complete then return false
           if (!requirement.isMatching(user, result))
               return false;
           return true;
    }

}
