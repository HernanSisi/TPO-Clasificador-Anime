package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Boton extends JButton {
    int alto;
    int ancho;
    Color color;
    Color colorHover;
    Font font;
    public Boton(int alto, int ancho, Color color, Font font, Color colorHover) {
        this.alto = alto;
        this.ancho = ancho;
        this.color = color;
        this.colorHover = colorHover;
        this.font = font;
    }
    public JButton getBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(font);
        boton.setBackground(color);
        boton.setPreferredSize(new Dimension(ancho, alto));
        boton.setFocusPainted(false);
        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(colorHover);
                boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                boton.setBackground(color);
            }
        });
        return boton;
    }
    public void actualizar(int alto, int ancho) {
        this.alto = alto;
        this.ancho = ancho;
    }
    public void actualizar(Color color) {
        this.color = color;
    }
    public void actualizar(Font font) {
        this.font = font;
    }
}
