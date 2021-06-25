package org.chuck.model;

/**
 * Created by Administrator on 15-12-4.
 */
public interface ITreeNode {
    public boolean isHaveChildren();
    public String getCode();
    public int getLevel();
    public String getParentCode();
    public String getName();
    public boolean isExpand();
}
