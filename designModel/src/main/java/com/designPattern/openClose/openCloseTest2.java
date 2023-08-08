package com.designPattern.openClose;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/8/8 14:56
 */
public class openCloseTest2 {
    public static void main(String[] args) {

        Grade grade = new Grade();
        grade.drawsShape(new Circles());
    }
}

class Grade{
    public void drawsShape(Shapes shapes){
        shapes.draw();
    }
}

abstract class Shapes{

    int m_type;
    public abstract void draw();

}
class Rectangles extends Shapes{
    Rectangles(){
        super.m_type =1;
    }

    @Override
    public void draw() {
        System.out.println("绘制矩形");
    }
}
class Circles extends Shapes{
    Circles(){
        super.m_type =2;
    }

    @Override
    public void draw() {
        System.out.println("绘制原型");
    }
}
class traingle extends Shapes{

    traingle(){
        super.m_type =3;
    }    @Override
    public void draw() {

        System.out.println("绘制三角形");
    }
}
