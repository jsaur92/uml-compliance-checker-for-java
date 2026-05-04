package com.umlcc.model;

/**
 * The result of a compliance evaluation.
 * @author Joe Hardy
 */
public class EvaluationResult {
    private Warning warning;
    private JavaThing thisThing;
    private JavaThing otherThing;
    private JavaThing parent;
    private Config config;

    private final static String THIS_CODE = "$THIS";
    private final static String OTHER_CODE = "$OTHER";
    private final static String PARENT_CODE = "$PARENT";

    /**
     * Constructor for an EvaluationResult that does not involve comparison.
     * @param warning the warning of this EvaluationResult.
     * @param thisThing the JavaThing this EvaluationResult goes with.
     */
    public EvaluationResult(Warning warning, JavaThing thisThing) {
        this.warning = warning;
        this.thisThing = thisThing;
        this.otherThing = null;
        this.parent = null;
        config = Config.getInstance();
    }

    /**
     * Constructor for an EvaluationResult that involves comparison.
     * @param warning the warning of this EvaluationResult.
     * @param thisThing the JavaThing this EvaluationResult goes with.
     * @param otherThing the JavaThing that thisThing is being compared to.
     */
    public EvaluationResult(Warning warning, JavaThing thisThing, JavaThing otherThing) {
        this.warning = warning;
        this.thisThing = thisThing;
        this.otherThing = otherThing;
        this.parent = null;
        config = Config.getInstance();
    }

    /**
     * Mutator method for parent.
     * @param parent the new parent.
     */
    public void setParent(JavaThing parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        String to = config.getWarningMessage(warning);

        while (to.contains(THIS_CODE)) {
            int i = to.indexOf(THIS_CODE);
            to = to.substring(0, i) + thisThing.getName() + to.substring(i+THIS_CODE.length());
        }

        while (to.contains(OTHER_CODE)) {
            int i = to.indexOf(OTHER_CODE);
            to = to.substring(0, i) + otherThing.getName() + to.substring(i+OTHER_CODE.length());
        }

        while (to.contains(PARENT_CODE)) {
            int i = to.indexOf(PARENT_CODE);
            to = to.substring(0, i) + parent.getName() + to.substring(i+PARENT_CODE.length());
        }

        return to;
    }
}
