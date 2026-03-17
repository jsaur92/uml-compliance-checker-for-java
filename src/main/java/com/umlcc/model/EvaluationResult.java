package com.umlcc.model;

/**
 * The result of a compliance evaluation.
 * @author Joe Hardy
 */
public class EvaluationResult {
    private Warning warning;
    private JavaThing thisThing;
    private JavaThing otherThing;
    private Config config;

    /**
     * Constructor for an EvaluationResult that does not involve comparison.
     * @param warning the warning of this EvaluationResult.
     * @param thisThing the JavaThing this EvaluationResult goes with.
     */
    public EvaluationResult(Warning warning, JavaThing thisThing) {
        this.warning = warning;
        this.thisThing = thisThing;
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
        config = Config.getInstance();
    }

    @Override
    public String toString() {
        return config.getWarningMessage(warning) + " | " + thisThing.getName();
    }
}
