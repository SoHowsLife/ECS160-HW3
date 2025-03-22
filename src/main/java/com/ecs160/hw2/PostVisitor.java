package com.ecs160.hw2;

public interface PostVisitor {
    void visit(SinglePost single);
    void visit(Thread thread);
}
