package cn.imeth.android.image.slider.transformers;

import android.view.View;

import cn.imeth.android.animation.ViewHelper;

public class CubeInTransformer extends BaseTransformer {

	@Override
	protected void onTransform(View view, float position) {
		// Rotate the fragment on the left or right edge
        ViewHelper.setPivotX(view,position > 0 ? 0 : view.getWidth());
        ViewHelper.setPivotY(view, 0);
        ViewHelper.setRotation(view,-90f * position);
	}

	@Override
	public boolean isPagingEnabled() {
		return true;
	}

}
