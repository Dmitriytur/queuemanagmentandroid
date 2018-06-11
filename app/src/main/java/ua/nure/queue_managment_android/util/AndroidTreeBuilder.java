package ua.nure.queue_managment_android.util;

import android.content.Context;

import com.unnamed.b.atv.model.TreeNode;

import java.util.ArrayList;
import java.util.List;

import ua.nure.queue_managment_android.holder.CategoryNodeHolder;
import ua.nure.queue_managment_android.model.CategoryEntity;

public class AndroidTreeBuilder {

    public static TreeNode buildTree(CategoryEntity categoryRoot, Context context) {
        TreeNode root = TreeNode.root();
        categoryRoot.setValue("Categories");
        TreeNode parent = buildNode(categoryRoot, context);
        root.addChild(parent);
        return root;
    }


    private static TreeNode buildNode(CategoryEntity categoryNode, Context context) {
        TreeNode node = new TreeNode(categoryNode).setViewHolder(new CategoryNodeHolder(context));
        List<TreeNode> childNodes = new ArrayList<>();
        for (CategoryEntity categoryEntity : categoryNode.getOptions()) {
            childNodes.add(buildNode(categoryEntity, context));
        }
        node.addChildren(childNodes);
        return node;
    }
}
