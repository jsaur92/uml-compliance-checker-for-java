package com.umlcc.model;

import java.util.ArrayList;

/**
 * The base object that Java classes, methods, and variables inherit from.
 * @author Joe Hardy
 */
public abstract class JavaThing {
    protected String name;
    protected ArrayList<Modifier> modifiers;
    protected String comment;
    protected Config config;

    /**
     * Constructor for the JavaThing class.
     * @param name the name of this thing.
     * @param modifiers all modifiers for this thing including privacy modifiers.
     * @param comment the comment placed above this thing.
     */
    public JavaThing(String name, ArrayList<Modifier> modifiers, String comment) {
        this.name = name;
        this.modifiers = modifiers;
        this.comment = comment;
        this.config = Config.getInstance();
    }

    /**
     * Evaluates the compliance of this thing, but only the styleguide parts
     * since there is not any other JavaThing to compare with.
     * @return list of evaluation results.
     */
    public abstract ArrayList<EvaluationResult> checkCompliance();

    /**
     * Evaluates the compliance of this thing by comparing it to another thing.
     * @param other the other JavaThing to compare to.
     * @return list of evaluation results.
     */
    public abstract ArrayList<EvaluationResult> checkCompliance(JavaThing other);

    /**
     * Compares this JavaThing with another.
     * @param other the other JavaThing to compare to.
     * @return true if the JavaThings are equal, false otherwise.
     */
    public boolean equals(JavaThing other) {
        if (!name.equals(other.getName())) return false;
        if (!modifiers.equals(other.getModifiers())) return false;
        // Should comments be part of equal?
        return true;
    }

    /**
     * Checks to see if this thing has a Javadoc comment.
     * @return true if this has a Javadoc comment, false otherwise.
     */
    public boolean hasJavaDoc() {
        return comment != null && JavaDoc.isJavaDoc(comment);
    }

    /**
     * Get a JavaDoc object made from this thing's comment.
     * @return the Javadoc in the form of a JavaDoc object.
     */
    public JavaDoc getJavaDoc() {
        if (!hasJavaDoc()) return null;
        return new JavaDoc(comment);
    }

    /**
     * Mutator method for comment.
     * @param comment the new comment to set comment to.
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Accessor method for name.
     * @return this JavaThing's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Accessor method for modifiers.
     * @return this JavaThing's modifiers.
     */
    public ArrayList<Modifier> getModifiers() {
        return modifiers;
    }

    /**
     * Attempt adding a warning to the EvaluationResults of a JavaThing.
     * Used for non-comparing compliance checks.
     * @param results the ArrayList of EvaluationResults to potentially add to.
     * @param warning the warning to check for.
     */
    protected void tryAddWarning(ArrayList<EvaluationResult> results, Warning warning) {
        if (config.hasWarning(warning)) results.add(
                new EvaluationResult(warning, this)
        );
    }

    /**
     * Attempt adding a warning to the EvaluationResults of a JavaThing.
     * Used for comparing compliance checks.
     * @param results the ArrayList of EvaluationResults to potentially add to.
     * @param warning the warning to check for.
     * @param other the JavaThing to compare to.
     */
    protected void tryAddWarning(ArrayList<EvaluationResult> results, Warning warning, JavaThing other) {
        if (config.hasWarning(warning)) results.add(
                new EvaluationResult(warning, this, other)
        );
    }

}
