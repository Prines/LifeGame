package lifegame;

import java.awt.Graphics;  
import java.awt.Image;  
import java.awt.event.ActionEvent;  
import java.awt.event.ActionListener;  
  
import javax.swing.JPanel;  
import javax.swing.Timer;  
import java.math.*;
 
  
public class WorldMap extends JPanel {  
  
  private static final long serialVersionUID = -336975817478756636L;  
  
  private final int width = 20;  
  
  private final int height = 20;  
  
  private final char WORLD_MAP_NONE = 'N';  
  
  private final char WORLD_MAP_ALIVE = 'A';  
   
  private char  world3[][]=new char[30][30]; 
  
     
  //当前细胞下一状态 
  private char[][] nextStatus = new char[world3.length][world3[0].length];  
  //当前状态
  private char[][] tempStatus = new char[world3.length][world3[0].length];  
  
  private Timer timer; //Timer类
  
  // 动画帧之间的延时，每秒60帧  
  private final int DELAY_TIME = 1000;  
  
  public WorldMap() {  
    this.startAnimation();  
  }  
  
    /** 
     * 画图形界面 
     */   
    protected void paintComponent(Graphics g) {  
        super.paintComponent(g);  
        for (int i = 0; i < nextStatus.length; i++) {  
            for (int j = 0; j < nextStatus[i].length; j++) {  
                if (nextStatus[i][j] == WORLD_MAP_ALIVE) {  
                    g.fillRect( j * width, i * height, width, height);  
                } else {  
                	 g.drawRect( j * width, i * height, width, height); 
                }  
            }  
        }  
    }  
  
    /** 
     * 改变细胞状态 
        */  
    private void changeCellStatus() {  
        for (int row = 0; row < nextStatus.length; row++) {  
            for (int col = 0; col < nextStatus[row].length; col++) {  
  
                switch (neighborsCount(row, col)) {  
                case 0:  
                case 1:  
                case 4:  
                case 5:  
                case 6:  
                case 7:  
                case 8:  
                    nextStatus[row][col] = WORLD_MAP_NONE;  
                    break;  
                case 2:  
                    nextStatus[row][col] = tempStatus[row][col];  
                    break;  
                case 3:  
                    nextStatus[row][col] = WORLD_MAP_ALIVE;  
                    break;  
                }  
            }  
        }  
        copyWorldMap();  
    }  
  
    /** 
     * 获取当前坐标点临近细胞个数 
     *  
         */  
    private int neighborsCount(int row, int col) {  
        int count = 0, r = 0, c = 0;  
  
        for (r = row - 1; r <= row + 1; r++) {  
            for (c = col - 1; c <= col + 1; c++) {  
                if (r < 0 || r >= tempStatus.length || c < 0  
                        || c >= tempStatus[0].length) {  
                    continue;  
                } //边界跳过 
                if (tempStatus[r][c] == WORLD_MAP_ALIVE) {  
                    count++;  
                }  
            }  
        }  
        //减去自己的情况
        if (tempStatus[row][col] == WORLD_MAP_ALIVE) {  
            count--;  
        }  
        return count;  
    }  
  
    /** 
     * 开始动画 
         */  
    private void startAnimation() {
    	int i,j;
    	for(i=0;i<30;i++)
    		for(j=0;j<30;j++) {
    			if(Math.random()>0.5) 
    				world3[i][j]='N';
    			else
    				world3[i][j]='A';
    		}
        for (int row = 0; row < world3.length; row++) {  
            for (int col = 0; col < world3[0].length; col++) {  
                nextStatus[row][col] = world3[row][col];  
                tempStatus[row][col] = world3[row][col];  
            }  
        }  
        // 创建计时器  
        timer = new Timer(DELAY_TIME, new ActionListener() {  
  
            @Override  
            public void actionPerformed(ActionEvent e) {  
                changeCellStatus();  
                repaint();  
            }  
        });  
        // 开启计时器  
        timer.start();  
    }  
  
    /** 
     * 复制地图 
     */  
    private void copyWorldMap() {  
        for (int row = 0; row < nextStatus.length; row++) {  
            for (int col = 0; col < nextStatus[row].length; col++) {  
                tempStatus[row][col] = nextStatus[row][col];  
            }  
        }  
    }  
}  
