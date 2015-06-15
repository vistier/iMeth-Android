package cn.imeth.android.view.tree;

import android.content.Context;
import android.view.View;

/**
 * Created by 戴文龙（daiwenlong@icifit.com） on 2015/3/1 0001.
 */
public abstract class TreeViewHolderAdapter<T, Holder extends TreeViewHolderAdapter.ViewHolder> extends AbstractTreeViewAdapter<T> {

	public TreeViewHolderAdapter(Context context, TreeStateManager<T> treeStateManager, int numberOfLevels) {
		super(context, treeStateManager, numberOfLevels);
	}

	@Override
	public View getNewChildView(TreeNodeInfo<T> node) {
		View view = inflateView(node);
		Holder holder = initViewHolder(view);
		view.setTag(holder);

		fillViewHolder(holder, node);

		return view;
	}

	@Override
	public View updateView(View view, TreeNodeInfo<T> node) {
		Holder holder = (Holder) view.getTag();

		if(holder == null) {
			holder = initViewHolder(view);
			view.setTag(holder);
		}

		fillViewHolder(holder,node);

		return view;
	}

	protected abstract  View inflateView(TreeNodeInfo<T> node);
	protected abstract void fillViewHolder(Holder holder, TreeNodeInfo<T> node);
	protected abstract Holder initViewHolder(View view);

	public static class ViewHolder {


	}
}
