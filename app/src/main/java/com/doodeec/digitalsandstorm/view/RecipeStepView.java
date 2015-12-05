package com.doodeec.digitalsandstorm.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doodeec.digitalsandstorm.R;
import com.doodeec.digitalsandstorm.model.RecipeStep;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Custom view used to show a single recipe step
 *
 * @author Dusan Bartos
 */
public class RecipeStepView extends RelativeLayout {

    @Bind(R.id.recipe_step_position)
    TextView mPosition;
    @Bind(R.id.recipe_step_description)
    TextView mStepDescription;

    private RecipeStep mRecipeStep;

    public RecipeStepView(Context context) {
        this(context, null);
    }

    public RecipeStepView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public RecipeStepView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.view_recipe_step, this);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ButterKnife.bind(this);
    }

    /**
     * Binds Recipe step instance to this view
     * Invalidates view
     *
     * @param step recipe step
     */
    public void setStep(RecipeStep step) {
        mRecipeStep = step;
        invalidate();
    }

    @Override
    public void invalidate() {
        super.invalidate();
        mPosition.setText(String.format("%d.", mRecipeStep.getPosition() + 1));
        mStepDescription.setText(mRecipeStep.getDescription());
    }
}
