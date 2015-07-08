package cn.imeth.android.view.tree;

/**
 * Information about the node.
 * 
 * @param <T>
 *            type of the id for the tree
 */
public class TreeNodeInfo<T> {

    private final T entity;
    private final int level;
    private final boolean withChildren;
    private final boolean visible;
    private final boolean expanded;

    /**
     * Creates the node information.
     * 
     * @param entity
     *            id of the node
     * @param level
     *            level of the node
     * @param withChildren
     *            whether the node has children.
     * @param visible
     *            whether the tree node is visible.
     * @param expanded
     *            whether the tree node is expanded
     * 
     */
    public TreeNodeInfo(final T entity, final int level,
            final boolean withChildren, final boolean visible,
            final boolean expanded) {
        super();
        this.entity = entity;
        this.level = level;
        this.withChildren = withChildren;
        this.visible = visible;
        this.expanded = expanded;
    }

	/**
	 * 请使用getEntity
	 *
	 * @return
	 */
	@Deprecated
    public T getId() {
        return entity;
    }

	public T getEntity() {
		return entity;
	}

    public boolean isWithChildren() {
        return withChildren;
    }

    public boolean isVisible() {
        return visible;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public String toString() {
        return "TreeNodeInfo [entity=" + entity + ", level=" + level
                + ", withChildren=" + withChildren + ", visible=" + visible
                + ", expanded=" + expanded + "]";
    }

}