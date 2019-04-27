package com.dp.structural.flyweight;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

//Encapsulates complexity of flyweight creation
//Flyweight factory decides whether to re-use existing
//flyweight or to create a new object.
public class TreeFactory
{
    private static Map<String, TreeType> treeTypes = new HashMap<>();

    public static TreeType getTreeType(String name, Color color, String otherTreeData)
    {
        TreeType result = treeTypes.get(name);
        if (result == null) {
            result = new TreeType(name, color, otherTreeData);
            treeTypes.put(name, result);
        }
        return result;
    }
}
