package com.designPattern.openClose;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/8/8 11:33
 */
public class openCloseTest {
    public static void main(String[] args) {

        GradhicEditor gradhicEditor = new GradhicEditor();
        gradhicEditor.drawShape(new Circle());

    }


}

// 缺点：不对使用方关闭
class Shape{

    int m_type;

}
class Rectangle extends Shape{
     Rectangle(){
        super.m_type =1;
    }
}
class Circle extends Shape{
    Circle(){
        super.m_type =2;
    }
}

class GradhicEditor{
    public void drawShape(Shape shape){

        if (shape.m_type == 1){

            drawRectangle(shape);
        }
        else if (shape.m_type == 2){
            drawCircle(shape);
        }

    }

    private void drawCircle(Shape shape) {

        System.out.println("圆形");
    }

    private void drawRectangle(Shape shape) {

        System.out.println("矩形");
    }
}
