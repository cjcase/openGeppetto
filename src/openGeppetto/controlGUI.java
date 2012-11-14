/*
 * controlGUI.java
 *
 * Created on Nov 6, 2012, 4:40:34 PM
 */
package openGeppetto;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Code by cjcase
public class controlGUI extends javax.swing.JFrame implements ActionListener{
    
    //Global Variables
    controlAdapter bot;
    Thread handThread;
    Thread botVisionThread;
    static int sliderMax = 10000;
    //public controlObserver conObs;
    
    
    /** Creates new form controlGUI */
    public controlGUI() {
        initComponents();
        this.setLocationRelativeTo(null);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        connectBtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        showKinectBtn = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        centerBtn = new javax.swing.JButton();
        rollSlider = new javax.swing.JSlider();
        panSlider = new javax.swing.JSlider();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        headBtn = new javax.swing.JButton();
        exitBtn = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        estopBtn = new javax.swing.JButton();
        rotateSlider = new javax.swing.JSlider();
        fwdSlider = new javax.swing.JSlider();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        walkBtn = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        stopLbl = new javax.swing.JLabel();
        stopBtn = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        botViewBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("openGeppeto Kinect Example");
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Connection"));

        connectBtn.setText("Connect");
        connectBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectBtnActionPerformed(evt);
            }
        });

        jLabel1.setText("Robot IP: ");

        jTextField1.setText("10.25.71.190");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(connectBtn)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(connectBtn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Kinect HandTracking"));

        showKinectBtn.setText("Show HandTracking");
        showKinectBtn.setEnabled(false);
        showKinectBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showKinectBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(showKinectBtn)
                .addContainerGap(35, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(showKinectBtn)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("AIBO Test Head Control"));

        centerBtn.setText("Center Head");
        centerBtn.setEnabled(false);
        centerBtn.setName("centerBtn"); // NOI18N
        centerBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                centerBtnActionPerformed(evt);
            }
        });

        rollSlider.setMaximum(10000);
        rollSlider.setMinimum(-10000);
        rollSlider.setValue(0);
        rollSlider.setEnabled(false);
        rollSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                rollSliderStateChanged(evt);
            }
        });

        panSlider.setMaximum(10000);
        panSlider.setMinimum(-10000);
        panSlider.setValue(0);
        panSlider.setEnabled(false);
        panSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                panSliderStateChanged(evt);
            }
        });

        jLabel2.setText("Head Pan");

        jLabel3.setText("Head Nod");

        headBtn.setText("Start Head");
        headBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                headBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 119, Short.MAX_VALUE)
                        .addComponent(headBtn))
                    .addComponent(rollSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(centerBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(headBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rollSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(centerBtn)
                .addGap(12, 12, 12))
        );

        exitBtn.setText("Exit");
        exitBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitBtnActionPerformed(evt);
            }
        });

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("AIBO Test Walk Control"));

        estopBtn.setText("Full Stop");
        estopBtn.setEnabled(false);
        estopBtn.setName("centerBtn"); // NOI18N
        estopBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                estopBtnActionPerformed(evt);
            }
        });

        rotateSlider.setMaximum(10000);
        rotateSlider.setMinimum(-10000);
        rotateSlider.setValue(0);
        rotateSlider.setEnabled(false);
        rotateSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                rotateSliderStateChanged(evt);
            }
        });

        fwdSlider.setMaximum(10000);
        fwdSlider.setMinimum(-10000);
        fwdSlider.setValue(0);
        fwdSlider.setEnabled(false);
        fwdSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                fwdSliderStateChanged(evt);
            }
        });

        jLabel4.setText("Forward");

        jLabel5.setText("Rotate");

        walkBtn.setText("Start Walk");
        walkBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                walkBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(fwdSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(96, 96, 96))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addContainerGap(240, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 169, Short.MAX_VALUE)
                        .addComponent(walkBtn)
                        .addGap(51, 51, 51))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(rotateSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(80, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(estopBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
                        .addGap(51, 51, 51))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4))
                    .addComponent(walkBtn))
                .addGap(12, 12, 12)
                .addComponent(fwdSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rotateSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(estopBtn)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Robot Status"));

        jLabel6.setForeground(new java.awt.Color(255, 0, 0));
        jLabel6.setText("Disconnected");

        stopLbl.setForeground(new java.awt.Color(255, 0, 0));
        stopLbl.setText("Blocked");

        stopBtn.setText("Unblock");
        stopBtn.setEnabled(false);
        stopBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(stopLbl)
                    .addComponent(stopBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(stopLbl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(stopBtn)
                .addContainerGap())
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Robot Vision"));

        botViewBtn.setText("Show Bot Vision");
        botViewBtn.setEnabled(false);
        botViewBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botViewBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(botViewBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(107, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(botViewBtn)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(6, 6, 6)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(exitBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(exitBtn)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void connectBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connectBtnActionPerformed
    //Start control through the adapter
    String ip = jTextField1.getText();
    bot = new controlAdapter(bot.AIBO, ip);
    
    stopBtn.setEnabled(true);
    showKinectBtn.setEnabled(true);
    botViewBtn.setEnabled(true);
    
    /*Slider Bounds
    panSlider.setMaximum(sliderMax);
    panSlider.setMinimum(-sliderMax);
    panSlider.setValue(0);
        
    rollSlider.setMaximum(sliderMax);
    rollSlider.setMinimum(-sliderMax);
    rollSlider.setValue(0);*/
}//GEN-LAST:event_connectBtnActionPerformed

private void showKinectBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showKinectBtnActionPerformed
    //Launch handtracking module
    if(bot.isConnected()){
        HandTrackerFrame nui = new HandTrackerFrame();
        nui.bot = this.bot;
        handThread = new Thread(nui);    
        handThread.start();    
    }
}//GEN-LAST:event_showKinectBtnActionPerformed

private void exitBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitBtnActionPerformed
    //End all
    System.exit(0);
}//GEN-LAST:event_exitBtnActionPerformed

private void centerBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_centerBtnActionPerformed
    //Return head position to center
    if(bot.isConnected()){
        panSlider.setValue(0);
        rollSlider.setValue(0);
        bot.panHead(panSlider.getValue()/(float)sliderMax);
        bot.nodHead(rollSlider.getValue()/(float)sliderMax);
    } 
}//GEN-LAST:event_centerBtnActionPerformed

private void panSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_panSliderStateChanged
    //Pan head with slider
    if(bot.isConnected())
        bot.panHead(panSlider.getValue()/(float)sliderMax);
}//GEN-LAST:event_panSliderStateChanged

private void rollSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_rollSliderStateChanged
    //Nod head with slider
    if(bot.isConnected())
        bot.nodHead(rollSlider.getValue()/(float)sliderMax);
}//GEN-LAST:event_rollSliderStateChanged

    private void estopBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_estopBtnActionPerformed
        //Stop Walking!
        if(bot.isConnected()){
            fwdSlider.setValue(0);
            rotateSlider.setValue(0);
            bot.goFwd(fwdSlider.getValue()/(float)sliderMax);
            bot.strafe(0.0); //Not used at the moment, but needed for smooth refresh movement
            bot.rotate(rotateSlider.getValue()/(float)sliderMax);
           
        }
    }//GEN-LAST:event_estopBtnActionPerformed

    private void rotateSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_rotateSliderStateChanged
    //Rotate walk
        if(bot.isConnected())            
            bot.rotate(rotateSlider.getValue()/(float)sliderMax);
    }//GEN-LAST:event_rotateSliderStateChanged

    private void fwdSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_fwdSliderStateChanged
    //Walk forward
        if(bot.isConnected())
            bot.goFwd(fwdSlider.getValue()/(float)sliderMax);
    }//GEN-LAST:event_fwdSliderStateChanged

    private void stopBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopBtnActionPerformed
        //Toggle stop on robot
        if(!bot.getStopped()){
            bot.stop();
            stopLbl.setText("Blocked");
            stopBtn.setText("Blocked");
            stopLbl.setForeground(Color.red);
        } else {
            bot.start();
            stopLbl.setText("Unblocked");
            stopBtn.setText("Unblocked");
            stopLbl.setForeground(Color.green);
        }
    }//GEN-LAST:event_stopBtnActionPerformed

    private void headBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_headBtnActionPerformed
        //Start the head actions
        if(bot.isConnected())
            bot.startHead();
            panSlider.setEnabled(true);
            rollSlider.setEnabled(true);
            centerBtn.setEnabled(true);
    }//GEN-LAST:event_headBtnActionPerformed

    private void walkBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_walkBtnActionPerformed
        //Start the walk actions
        if(bot.isConnected())
            bot.startWalk();
            fwdSlider.setEnabled(true);
            rotateSlider.setEnabled(true);
            estopBtn.setEnabled(true);
    }//GEN-LAST:event_walkBtnActionPerformed

    private void botViewBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botViewBtnActionPerformed
        //Call viewAIBO and start fetching RAW vision
        if(bot.isConnected()){
            bot.startRaw();
            VisionFrame test = new VisionFrame(bot.getHost());
            botVisionThread = new Thread(test);    
            botVisionThread.start();
        }        
    }//GEN-LAST:event_botViewBtnActionPerformed

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
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(controlGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(controlGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(controlGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(controlGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new controlGUI().setVisible(true);
            }
         });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botViewBtn;
    private javax.swing.JButton centerBtn;
    private javax.swing.JButton connectBtn;
    private javax.swing.JButton estopBtn;
    private javax.swing.JButton exitBtn;
    private javax.swing.JSlider fwdSlider;
    private javax.swing.JButton headBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JSlider panSlider;
    private javax.swing.JSlider rollSlider;
    private javax.swing.JSlider rotateSlider;
    private javax.swing.JButton showKinectBtn;
    private javax.swing.JButton stopBtn;
    private javax.swing.JLabel stopLbl;
    private javax.swing.JButton walkBtn;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
