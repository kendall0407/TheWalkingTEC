/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import javax.swing.*;
import java.util.*;
import Models.*;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;

/**
 *
 * @author josed
 */
public class MainFrame extends JFrame{
    Player p1 = new Player("Jugador 1", new Player1Strategy());
    Player p2 = new Player("Jugador 2", new Player2Strategy());
    
    // Componentes que el método usa desde fuera (se declaran en la clase para ser accesibles)
    private JLabel selectedArtLabel;
    private JLabel selectedImageLabel;
    private JButton generateBtn = new JButton();
    private JButton sendBtn = new JButton("Enviar");
    private JButton clearBtn = new JButton("Limpiar");
    private JTextField comboField = new JTextField();
    private JTextArea logArea = new JTextArea(10,30);
    private JLabel hpLabel;
    private JButton atacarBtn;
    private List<JButton> p1ArtSelectButtons;
    private List<List<JButton>> p1AttackDetailButtons;

    private JButton p2GenerarBtn;
    private JButton p2LimpiarBtn;
    private JButton p2SendBtn;
    private JTextField p2ComboField;
    private JTextArea p2Bitacora;
    private JLabel p2HpLabel; // Etiqueta de vida para J2


    public MainFrame(){
        setTitle("Combate de Artes Marciales");
        setSize(1400,800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(1,3));

        add(buildPlayer1Panel());
        add(buildControlPanel());
        add(buildPlayer2Panel());

        setVisible(true);
    }


    public JPanel buildPlayer1Panel(){
        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());

        // TITULO
        JLabel title = new JLabel("Jugador 1", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        p.add(title, BorderLayout.NORTH);

        // CENTRO: lista de 3 artes en la parte superior y seleccion/imagen/controles abajo
        JPanel main = new JPanel(new BorderLayout());

        // --- Panel superior: los 3 artes con sus 3 ataques cada uno ---
        JPanel artsRow = new JPanel(new GridLayout(1, 3, 10, 10));
        artsRow.setPreferredSize(new Dimension(1200, 300)); // Reducido de 350 a 300

        // Vamos a guardar referencias a botones para luego bloquearlos
        p1ArtSelectButtons = new ArrayList<>();
        p1AttackDetailButtons = new ArrayList<>();

        for (int i = 0; i < 3; i++){
            MartialArt art = p1.arts.get(i);
            JPanel artPanel = new JPanel();
            artPanel.setLayout(new BorderLayout(5, 5));
            artPanel.setBorder(BorderFactory.createTitledBorder(art.getName()));
            artPanel.setPreferredSize(new Dimension(300, 280)); // Reducido de 320 a 280

            // List of attacks with a Detalles button for each
            JPanel attacksList = new JPanel();
            attacksList.setLayout(new BoxLayout(attacksList, BoxLayout.Y_AXIS));
            attacksList.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Reducido de 8

            List<JButton> detailButtonsForArt = new ArrayList<>();
            for (Move m : art.getMoves()){
                JPanel row = new JPanel();
                row.setLayout(new BoxLayout(row, BoxLayout.Y_AXIS));
                row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50)); // Reducido de 60 a 50
                row.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2)); // Reducido de 3
                
                JLabel moveLabel = new JLabel(m.getName() + " (Daño: " + m.getDamage() + ")");
                moveLabel.setFont(new Font("Arial", Font.PLAIN, 11)); // Fuente más pequeña
                moveLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                
                JButton detalles = new JButton("Detalles");
                detalles.setPreferredSize(new Dimension(75, 22)); // Reducido
                detalles.setMaximumSize(new Dimension(75, 22));
                detalles.setFont(new Font("Arial", Font.PLAIN, 10)); // Fuente más pequeña
                detalles.setAlignmentX(Component.LEFT_ALIGNMENT);
                
                // Listener muestra daño y efectos especiales
                detalles.addActionListener(ev -> {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Ataque: ").append(m.getName()).append("\n");
                    sb.append("Daño base: ").append(m.getDamage()).append("\n");
                    if (m.heal() > 0) sb.append("Efecto: Cura " + m.heal() + " HP\n");
                    if (m.bonusDamage() > 0) sb.append("Efecto: Daño extra " + m.bonusDamage() + "\n");
                    if (m.selfDamage() > 0) sb.append("Efecto: Causa autodaño " + m.selfDamage() + "\n");
                    if (m.heal() == 0 && m.bonusDamage() == 0 && m.selfDamage() == 0) sb.append("Efecto: Ninguno (ataque básico)");
                    JOptionPane.showMessageDialog(this, sb.toString(), "Detalles - " + m.getName(), JOptionPane.INFORMATION_MESSAGE);
                });

                row.add(moveLabel);
                row.add(detalles);
                attacksList.add(row);
                attacksList.add(Box.createVerticalStrut(2)); // Reducido de 3 a 2
                detailButtonsForArt.add(detalles);
            }

            p1AttackDetailButtons.add(detailButtonsForArt);

            // ScrollPane para los ataques
            JScrollPane scrollAttacks = new JScrollPane(attacksList);
            scrollAttacks.setPreferredSize(new Dimension(280, 220)); // Reducido de 250 a 220
            scrollAttacks.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

            // Abajo de cada arte: boton Seleccionar
            JButton selectArtBtn = new JButton("Seleccionar");
            selectArtBtn.setPreferredSize(new Dimension(280, 28)); // Reducido de 30 a 28
            selectArtBtn.setFont(new Font("Arial", Font.PLAIN, 11)); // Fuente más pequeña
            final int artIndex = i;
            selectArtBtn.addActionListener(ev -> {
                // Actualiza zona de seleccion
                selectedArtLabel.setText("Seleccionado: " + art.getName());
                // intenta cargar imagen si existe
                try{
                    ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource(art.getImagePath())));
                    Image img = icon.getImage().getScaledInstance(220, 150, Image.SCALE_SMOOTH);
                    selectedImageLabel.setIcon(new ImageIcon(img));
                    selectedImageLabel.setText("");
                }catch(Exception ex){
                    selectedImageLabel.setIcon(null);
                    selectedImageLabel.setText(art.getName());
                }
                // habilitar boton generar
                generateBtn.setEnabled(true);
                // almacenar indice seleccionado
                generateBtn.putClientProperty("selectedArtIndex", artIndex);
            });

            artPanel.add(scrollAttacks, BorderLayout.CENTER);
            artPanel.add(selectArtBtn, BorderLayout.SOUTH);

            p1ArtSelectButtons.add(selectArtBtn);
            artsRow.add(artPanel);
        }

        main.add(artsRow, BorderLayout.NORTH);

        // --- Panel central de selección/imagen/generar/combo/enviar/limpiar ---
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());

        // zona de seleccion con imagen
        JPanel selectionArea = new JPanel(new BorderLayout());
        selectedArtLabel = new JLabel("No seleccionado", SwingConstants.CENTER);
        selectedArtLabel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        selectedImageLabel = new JLabel();
        selectedImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        selectedImageLabel.setPreferredSize(new Dimension(220,150));

        selectionArea.add(selectedArtLabel, BorderLayout.NORTH);
        selectionArea.add(selectedImageLabel, BorderLayout.CENTER);

        centerPanel.add(selectionArea, BorderLayout.WEST);

        // panel derecho con generar, combo, enviar, limpiar, vida y bitacora
        JPanel rightArea = new JPanel();
        rightArea.setLayout(new BoxLayout(rightArea, BoxLayout.Y_AXIS));

        // Generar
        generateBtn.setText("Generar");
        generateBtn.setEnabled(false);
        generateBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        rightArea.add(generateBtn);

        // Combo textfield
        comboField.setEditable(false);
        comboField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        rightArea.add(Box.createRigidArea(new Dimension(0,5)));
        rightArea.add(new JLabel("Combo generado:"));
        rightArea.add(comboField);
        rightArea.add(Box.createRigidArea(new Dimension(0,5)));

        // Enviar y Limpiar
        JPanel sendClear = new JPanel(new FlowLayout(FlowLayout.LEFT));
        sendBtn.setEnabled(false);
        sendClear.add(sendBtn);
        sendClear.add(clearBtn);
        sendClear.setAlignmentX(Component.LEFT_ALIGNMENT);
        rightArea.add(sendClear);

        // Vida y bitacora
        hpLabel = new JLabel("Vida: " + p1.hp);
        hpLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        rightArea.add(hpLabel);
        rightArea.add(Box.createRigidArea(new Dimension(0,5)));

        rightArea.add(new JLabel("Bitácora de ataques recibidos:"));
        JScrollPane logScroll = new JScrollPane(logArea);
        logArea.setEditable(false);
        logArea.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
        rightArea.add(logScroll);

        centerPanel.add(rightArea, BorderLayout.CENTER);

        main.add(centerPanel, BorderLayout.CENTER);

        p.add(main, BorderLayout.CENTER);

        // ===================== LÓGICA DE BOTONES =====================
        // Generar: crear 3-6 golpes del arte seleccionado
        generateBtn.addActionListener(e -> {
            Object idxObj = generateBtn.getClientProperty("selectedArtIndex");
            if (idxObj == null) return;
            int idx = (int) idxObj;
            MartialArt art = p1.arts.get(idx);

            int hits = 3 + new Random().nextInt(4); // 3-6
            List<Move> combo = new ArrayList<>();
            StringBuilder sb = new StringBuilder();
            sb.append("Arte: ").append(art.getName()).append(" - ");
            for (int i = 0; i < hits; i++){
                Move m = art.getMoves()[new Random().nextInt(art.getMoves().length)];
                combo.add(m);
                sb.append(m.getName())
                  .append(" (")
                  .append(m.getDamage())
                  .append(")");
                if (m.heal() > 0) sb.append("[Cura "+m.heal()+"]");
                if (m.bonusDamage() > 0) sb.append("[Extra "+m.bonusDamage()+"]");
                if (m.selfDamage() > 0) sb.append("[Costo "+m.selfDamage()+"]");
                if (i < hits - 1) sb.append(", ");
            }

            // Guardar combo en el boton send
            sendBtn.putClientProperty("combo", combo);
            comboField.setText(sb.toString());
            sendBtn.setEnabled(true);
        });

        // Enviar: bloquea todo el panel del J1 hasta que se presione Atacar
        sendBtn.addActionListener(e -> {
            @SuppressWarnings("unchecked")
            java.util.List<Move> combo = (java.util.List<Move>) sendBtn.getClientProperty("combo");
            if (combo == null || combo.isEmpty()) return;

            // bloquear: deshabilitar selectores, detalles, generar y enviar
            for (Component c : artsRow.getComponents()){
                // cada artPanel: tiene select button en SOUTH y detalles en el centro
                for (Component inner : ((JPanel)c).getComponents()){
                    if (inner instanceof JButton) inner.setEnabled(false);
                }
            }
            // deshabilitar cada select en nuestra lista
            for (JButton b : p1ArtSelectButtons) b.setEnabled(false);
            for (List<JButton> dbs : p1AttackDetailButtons) for (JButton db : dbs) db.setEnabled(false);
            generateBtn.setEnabled(false);
            sendBtn.setEnabled(false);

            // marcar que envió (lo usará el panel central/ controlador)
            p1.sent = true;
            p1.sentCombo = combo;
            
            // Limpiar la barra de combo
            comboField.setText("");
            
            if (p1.sent && p2.sent) atacarBtn.setEnabled(true);

            logArea.append("[ENVIADO] " + combo.size() + " movimientos.\n");
        });

        clearBtn.addActionListener(e -> {
            // reenable buttons
            for (JButton b : p1ArtSelectButtons) b.setEnabled(true);
            for (List<JButton> dbs : p1AttackDetailButtons) for (JButton db : dbs) db.setEnabled(true);
            generateBtn.setEnabled(false);
            sendBtn.setEnabled(false);
            // clear labels and image
            selectedArtLabel.setText("No seleccionado");
            selectedImageLabel.setIcon(null);
            selectedImageLabel.setText("");
            // solo limpiar el combo y flags; NO reiniciar vida ni limpiar bitácora
            comboField.setText("");
            p1.sent = false;
            p1.sentCombo = null;

        });

        return p;
    }

    
    
    public JPanel buildControlPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        panel.setBorder(BorderFactory.createTitledBorder("Acciones"));


        atacarBtn = new JButton("Atacar");
        atacarBtn.setEnabled(false);     // Solo se habilita cuando J1 y J2 han enviado combo

        JButton reasignarJ1Btn = new JButton("Reasignar J1");
        reasignarJ1Btn.setEnabled(true); // Se deshabilita cuando J1 presiona Enviar

        JButton reiniciarBtn = new JButton("Reiniciar");

        // Añadir al panel
        panel.add(atacarBtn);
        panel.add(reasignarJ1Btn);
        panel.add(reiniciarBtn);
        
        reiniciarBtn.addActionListener(e -> {
            reiniciarJuego();
        });

        atacarBtn.addActionListener(e -> {

    Boolean s1 = p1.isSent();
    Boolean s2 = p2.isSent();

    if (s1 == null || !s1 || s2 == null || !s2) {
        JOptionPane.showMessageDialog(this, 
                "Ambos jugadores deben enviar su combo antes de atacar.");
        return;
    }

    // Obtener combos
    List<Move> combo1 = p1.getSentCombo();
    List<Move> combo2 = p2.getSentCombo();

    if (combo1 == null || combo2 == null) return;

    // *** COMBATE ***
    int max = Math.max(combo1.size(), combo2.size());
    StringBuilder log1 = new StringBuilder(); // ataques QUE RECIBE J1
    StringBuilder log2 = new StringBuilder(); // ataques QUE RECIBE J2

    for (int i = 0; i < max; i++) {

        // --- J1 ataca primero si tiene golpe ---
        if (i < combo1.size()) {
            Move m = combo1.get(i);
            int dmg = m.getDamage();
            
            // Aplicar efectos especiales
            if (m.bonusDamage() > 0) dmg += m.bonusDamage();
            if (m.selfDamage() > 0) p1.hp -= m.selfDamage();
            if (m.heal() > 0) p1.hp += m.heal();
            
            p2.hp -= dmg;
            log2.append("J1").append(" atacó con ").append(m.getName())
                 .append(" (Daño: ").append(dmg).append(")\n");
        }

        // --- J2 ataca si tiene golpe ---
        if (i < combo2.size()) {
            Move m = combo2.get(i);
            int dmg = m.getDamage();
            
            // Aplicar efectos especiales
            if (m.bonusDamage() > 0) dmg += m.bonusDamage();
            if (m.selfDamage() > 0) p2.hp -= m.selfDamage();
            if (m.heal() > 0) p2.hp += m.heal();
            
            p1.hp -= dmg;
            log1.append("J2").append(" atacó con ").append(m.getName())
                 .append(" (Daño: ").append(dmg).append(")\n");
        }
    }
    
    //Comprobar si se terminó la partida
    
    if (p1.hp <= 0 || p2.hp <= 0){
        if (p1.hp != p2.hp){
            int ganador = Math.max(p1.hp, p2.hp);
            if (ganador == p1.hp){
                JOptionPane.showMessageDialog(this, "Partida Finalizada. Gana el Jugador 1");
                reiniciarJuego();
            } else {
                JOptionPane.showMessageDialog(this, "Partida Finalizada. Gana el Jugador 2");
                reiniciarJuego();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Partida Finalizada. Empate");
            reiniciarJuego();
        }
            
    } else{
    

    // Actualizar labels de vida
    
    hpLabel.setText("Vida: " + p1.hp);
    p2HpLabel.setText("Vida: " + p2.hp);

    // Añadir bitácoras (IDÉNTICAS para ambos)
    logArea.append("--- Ataque recibido ---\n" + log1.toString());
    p2Bitacora.append("--- Ataque recibido ---\n" + log2.toString());

    // Limpiar flags de turno
    p1.setSent(false);
    p1.setSentCombo(null);
    p2.setSent(false);
    p2.setSentCombo(null);
    
    // re-habilitar controles J1
    for (JButton b : p1ArtSelectButtons) b.setEnabled(true);
    for (List<JButton> dbs : p1AttackDetailButtons) for (JButton db : dbs) db.setEnabled(true);
    generateBtn.setEnabled(false); // obligar a re-seleccionar arte
    sendBtn.setEnabled(false);

    // re-habilitar controles J2
    if (p2GenerarBtn != null) p2GenerarBtn.setEnabled(true);
    if (p2LimpiarBtn != null) p2LimpiarBtn.setEnabled(true);
    if (p2SendBtn != null) p2SendBtn.setEnabled(true);

    atacarBtn.setEnabled(false); 
    JOptionPane.showMessageDialog(this, "Turno completado.");
    }
});
        // Reemplazar el listener de reasignarJ1Btn
        reasignarJ1Btn.addActionListener(e -> {
            // Reasignar 3 artes marciales aleatorios a J1
            p1.arts = MartialArtFactory.randomThree();
            
            // Ejecutar la lógica de limpiar combo
            for (JButton b : p1ArtSelectButtons) b.setEnabled(true);
            for (List<JButton> dbs : p1AttackDetailButtons) for (JButton db : dbs) db.setEnabled(true);
            generateBtn.setEnabled(false);
            sendBtn.setEnabled(false);
            selectedArtLabel.setText("No seleccionado");
            selectedImageLabel.setIcon(null);
            selectedImageLabel.setText("");
            comboField.setText("");
            p1.sent = false;
            p1.sentCombo = null;
           
            
            // Reconstruir el panel de artes
            rebuildPlayer1ArtsPanel();
        });
        panel.putClientProperty("atacarBtn", atacarBtn);
        panel.putClientProperty("reasignarJ1Btn", reasignarJ1Btn);
        panel.putClientProperty("reiniciarBtn", reiniciarBtn);

        return panel;
    }



    public JPanel buildPlayer2Panel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Jugador 2"));

        // PANEL SUPERIOR con botones, combo y VIDA
        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));

        JPanel topButtons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p2GenerarBtn = new JButton("Generar");
        p2LimpiarBtn = new JButton("Limpiar");

        topButtons.add(p2GenerarBtn);
        topButtons.add(p2LimpiarBtn);
        
        top.add(topButtons);

        // Fila con combo
        JPanel comboRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        comboRow.add(new JLabel("Combo:"));
        p2ComboField = new JTextField(20);
        p2ComboField.setEditable(false);
        comboRow.add(p2ComboField);
        top.add(comboRow);

        // Fila con VIDA (ahora visible)
        JPanel hpRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p2HpLabel = new JLabel("Vida: " + p2.hp);
        p2HpLabel.setFont(new Font("Arial", Font.BOLD, 14));
        hpRow.add(p2HpLabel);
        top.add(hpRow);

        panel.add(top, BorderLayout.NORTH);

        // BITÁCORA pequeña (más compacta)
        p2Bitacora = new JTextArea(6, 25);
        p2Bitacora.setEditable(false);
        p2Bitacora.setFont(new Font("Monospaced", Font.PLAIN, 10));
        JScrollPane scroll = new JScrollPane(p2Bitacora);
        panel.add(scroll, BorderLayout.CENTER);

        // PANEL INFERIOR con boton Enviar
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p2SendBtn = new JButton("Enviar");
        bottom.add(p2SendBtn);
        panel.add(bottom, BorderLayout.SOUTH);

        p2GenerarBtn.addActionListener(e -> {
            // Generar entre 3 y 6 movimientos
            int cantidad = 3 + (int)(Math.random() * 4);

            List<Move> combo = new ArrayList<>();
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < cantidad; i++) {
                MartialArt art = MartialArtFactory.random();
                Move mv = art.randomMove();
                combo.add(mv);

                sb.append(mv.getName())
                  .append(" (")
                  .append(art.getName())
                  .append(", Daño: ")
                  .append(mv.getDamage())
                  .append(")");
                
                if (mv.heal() > 0) sb.append("[Cura "+mv.heal()+"]");
                if (mv.bonusDamage() > 0) sb.append("[Extra "+mv.bonusDamage()+"]");
                if (mv.selfDamage() > 0) sb.append("[Costo "+mv.selfDamage()+"]");

                if (i < cantidad - 1) sb.append(", ");
            }

            p2SendBtn.putClientProperty("combo", combo);
            p2ComboField.setText(sb.toString());
            p2Bitacora.append("Combo generado: " + sb + "\n");
        });

        p2LimpiarBtn.addActionListener(e -> {
            p2ComboField.setText("");
            p2Bitacora.append("Combo limpiado.\n");
            p2SendBtn.putClientProperty("combo", null);
            p2.sent = false;
            p2.sentCombo = null;
        });

        p2SendBtn.addActionListener(e -> {
            @SuppressWarnings("unchecked")
            List<Move> combo = (List<Move>) p2SendBtn.getClientProperty("combo");

            if (combo == null) {
                p2Bitacora.append("No hay combo para enviar.\n");
                return;
            }

            p2GenerarBtn.setEnabled(false);
            p2LimpiarBtn.setEnabled(false);
            p2SendBtn.setEnabled(false);

            p2.sent = true;
            p2.sentCombo = combo;

            p2Bitacora.append("Enviado combo de " + combo.size() + " movimientos.\n");
            
            p2ComboField.setText("");

            if (Boolean.TRUE.equals(p1.sent) && Boolean.TRUE.equals(p2.sent) && atacarBtn != null) {
                atacarBtn.setEnabled(true);
                
            }
        });

        return panel;
    }

    // Reinicia el estado según lo pedido: HP = 200, bitácoras limpias, activar botones "Limpiar"
    private void reiniciarJuego() {
        // Restaurar vida
        p1.hp = 200;
        p2.hp = 200;

        // Limpiar bitácoras
        if (logArea != null) logArea.setText("");
        if (p2Bitacora != null) p2Bitacora.setText("");

        // Actualizar labels de vida (si ya están creadas)
        if (hpLabel != null) hpLabel.setText("Vida: " + p1.hp);
        if (p2HpLabel != null) p2HpLabel.setText("Vida: " + p2.hp);

        // Limpiar campos de combo
        if (comboField != null) comboField.setText("");
        if (p2ComboField != null) p2ComboField.setText("");

        // Resetear flags y combos
        p1.sent = false; p1.sentCombo = null;
        p2.sent = false; p2.sentCombo = null;

        // Activar botones "Limpiar" en ambos paneles
        if (clearBtn != null) clearBtn.setEnabled(true);
        if (p2LimpiarBtn != null) p2LimpiarBtn.setEnabled(true);

        // También reactivar controles mínimos para continuar (opcional)
        if (p2GenerarBtn != null) p2GenerarBtn.setEnabled(true);
        for (JButton b : p1ArtSelectButtons) b.setEnabled(true);
        for (List<JButton> dbs : p1AttackDetailButtons) for (JButton db : dbs) db.setEnabled(true);
        generateBtn.setEnabled(false);
        sendBtn.setEnabled(false);
        if (p2SendBtn != null) p2SendBtn.setEnabled(true);

    }

    // Nuevo método para reconstruir el panel de artes de J1
    private void rebuildPlayer1ArtsPanel() {
        removeAllListeners();
        // Obtener la ventana principal
        JPanel mainPanel = (JPanel) getContentPane().getComponent(0);
        mainPanel.removeAll();
        mainPanel.add(buildPlayer1Panel(), BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }
    
    private void removeAllListeners() {
    for (JButton b : p1ArtSelectButtons)
        for (var l : b.getActionListeners())
            b.removeActionListener(l);

    for (List<JButton> detailList : p1AttackDetailButtons)
        for (JButton db : detailList)
            for (var l : db.getActionListeners())
                db.removeActionListener(l);

    for (var l : generateBtn.getActionListeners())
        generateBtn.removeActionListener(l);

    for (var l : sendBtn.getActionListeners())
        sendBtn.removeActionListener(l);

    for (var l : clearBtn.getActionListeners())
        clearBtn.removeActionListener(l);
}
}
