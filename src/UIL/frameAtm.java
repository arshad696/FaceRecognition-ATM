/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIL;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import javax.swing.JOptionPane;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.IplImage;
import static org.bytedeco.javacpp.opencv_core.cvFlip;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.VideoInputFrameGrabber;

/**
 *
 * @author User
 */
public class frameAtm extends javax.swing.JFrame {
    
    FrameGrabber grabber;
    IplImage Iplimg;
    OpenCVFrameConverter.ToIplImage converter=new OpenCVFrameConverter.ToIplImage();
    BufferedImage buffImg;
    
    
    class captureImages implements Runnable//class to capture the image
    {
        protected volatile boolean run = false;
        protected volatile FrameGrabber gr;

        @Override
        public void run() 
        {
            try 
            {
                grabber=new VideoInputFrameGrabber(0);//to define the camera
                grabber.start();//start the camera
                while(run)
                {
                    Frame frame=grabber.grab();
                    Iplimg=converter.convertToIplImage(frame);//to convert frame to iplimage
                    if(Iplimg!=null)
                    {
                        buffImg=IpltoBuffered(Iplimg);
                        Graphics g=panelCam.getGraphics();
                        if (g.drawImage(buffImg, 0, 0, getWidth(), getHeight() -150 , 0, 0, buffImg.getWidth(), buffImg.getHeight(), null))
                        if(run == false)
                        {
                            //change the method here
                            System.out.println("Going to wait()");
			    this.wait();
                        }
                    }
                }
            } 
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        
        public BufferedImage IpltoBuffered(IplImage src) //method to convert ipl to buffered
        {
            OpenCVFrameConverter.ToIplImage grabberconverter = new OpenCVFrameConverter.ToIplImage();
            Java2DFrameConverter paintConverter = new Java2DFrameConverter();
            Frame frame = grabberconverter.convert(src);
            return paintConverter.getBufferedImage(frame,1);
        }
    }
    
    
    /**
     * Creates new form frameAtm
     */
    public frameAtm() {
        initComponents();
        //labelError.setVisible(false);
        labelError.setText("");
        capture();
    }
    
    private void capture()//method to call capture class
    {
        captureImages capt=new captureImages();
        Thread t=new Thread(capt);
        t.setDaemon(true);
        capt.run=true;
        t.start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jProgressBar1 = new javax.swing.JProgressBar();
        panelCam = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtAccountNum = new javax.swing.JTextField();
        btnRecognize = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        labelError = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelCam.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelCam.setPreferredSize(new java.awt.Dimension(320, 240));

        javax.swing.GroupLayout panelCamLayout = new javax.swing.GroupLayout(panelCam);
        panelCam.setLayout(panelCamLayout);
        panelCamLayout.setHorizontalGroup(
            panelCamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 448, Short.MAX_VALUE)
        );
        panelCamLayout.setVerticalGroup(
            panelCamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 281, Short.MAX_VALUE)
        );

        jLabel1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel1.setText("ATM");

        jLabel2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel2.setText("RECOGNIZE YOURSELF");

        txtAccountNum.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtAccountNum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAccountNumKeyTyped(evt);
            }
        });

        btnRecognize.setText("RECOGNIZE");
        btnRecognize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRecognizeActionPerformed(evt);
            }
        });

        labelError.setForeground(new java.awt.Color(255, 0, 0));
        labelError.setText("Error message");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(100, 100, 100)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelError, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelCam, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(176, 176, 176)
                        .addComponent(txtAccountNum, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(288, 288, 288)
                        .addComponent(btnRecognize))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(259, 259, 259)
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(300, 300, 300)
                        .addComponent(jLabel1)))
                .addContainerGap(100, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(labelError)
                .addGap(13, 13, 13)
                .addComponent(panelCam, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(txtAccountNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnRecognize, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRecognizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRecognizeActionPerformed
        
        if(txtAccountNum.getText().isEmpty())
        {
            //labelError.setVisible(true);
            labelError.setText("Please enter the account number");
        }
        else
        {
            //do the recognizing part
            //this is for testing
            FrameAuthenticate obj=new FrameAuthenticate("Arshad","Colombo",64114,1312113,buffImg);
            obj.show();
        }
    }//GEN-LAST:event_btnRecognizeActionPerformed

    private void txtAccountNumKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAccountNumKeyTyped

         char enter = evt.getKeyChar();
        if(!(Character.isDigit(enter) || enter==KeyEvent.VK_BACK_SPACE || enter==KeyEvent.VK_DELETE || enter==KeyEvent.VK_PERIOD))
        {
            JOptionPane.showMessageDialog(null,"enter number values only");
            evt.consume();
        }
    }//GEN-LAST:event_txtAccountNumKeyTyped

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frameAtm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frameAtm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frameAtm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frameAtm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frameAtm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRecognize;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel labelError;
    private javax.swing.JPanel panelCam;
    private javax.swing.JTextField txtAccountNum;
    // End of variables declaration//GEN-END:variables
}
