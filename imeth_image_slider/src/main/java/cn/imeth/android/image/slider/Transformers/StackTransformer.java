package cn.imeth.android.image.slider.transformers;

import android.view.View;

import cn.imeth.android.animation.ViewHelper;

public class StackTransformer extends BaseTransformer {

	@Override
	protected void onTransform(View view, float position) {
		ViewHelper.setTranslationX(view, position < 0 ? 0f : -view.getWidth() * position);
	}

}
