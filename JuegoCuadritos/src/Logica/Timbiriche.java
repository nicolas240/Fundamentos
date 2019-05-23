
package logica;

/**
 * @author brayanNicolas
 */
public class Timbiriche {
    
    public static int altura=7;
    public static int ancho=7;
    
    public static int alturaPixel=altura/40;
    public static int anchoPixel=ancho/40;
    
    public enum Estado{
        Blanco,//0
        Horizontal,//1
        Vertical,//2
        LasDos,//3
        BordeInferior,//4
        BordeDerecho,//5
        
        InferiorDerecho,//6
        VerticalInferior,//7
        VerticalDerecho,//8
        HorizontalDerecho,//9
        HorizontalInferior,//10
        
        LasDosDerecho,//11
        LasDosInferior,//12
        
        Punto,//Mirar si en la esquina tambien sirve
    };
    
    private Estado tablero[][]=new Estado[altura][ancho];  //Representación del tablero 
   
    private int finJuego=0;// 1 perdio, 2 gano
    private int CasillasLlenas=0; // hay 49 casillas
    private int Puntos1=0;// los puntos que tienen los dos jugadores
    private int Puntos2=0;
    
    public Estado[][] getArreglo() {
        if(tablero == null){
            Estado tablero[][] = new Estado[altura][ancho];
            crearTablero();
        }
        return tablero;
    }
    
  public void crearTablero(){
        //Inicializa el tablero
        for (int f=1;f <= altura-1;f++){
            for (int c=1;c < ancho-1;c++){
                tablero[f][c]=Estado.Blanco;
            }
        }
        CasillasLlenas=0;
        Puntos1 =0;
        Puntos2 =0;
        finJuego = 0;
    } 
  
  public void Clic(int x,int y){
    int f = y;
    int c = x;
    
    for(int i=0;i<altura;i++){
         for(int j=0;j<ancho;j++){
            if(tablero[i][j]==Estado.Blanco){
               if(c>((ancho)*40)-10){
                   if( f>=(j*40)&&f<=((j+1)*40-5) ){
                       tablero[i][j]=Estado.BordeDerecho;
                   }
                }
                if(f>((altura)*40)-10){
                    if( c>=(i*40)&&c<=((i+1)*40-5) ){
                       tablero[i][j] =Estado.BordeInferior;
                    }
                }
                if(tablero[i][j]==Estado.Blanco){
                    tablero[i][j] = VerticalHorizontal(c,f,i,j);
               } 
            }else{
            //Hacer if para LasDosDerecho e inferior para si se hace
            //el punto
                if(tablero[i][j]==Estado.LasDosDerecho){
                    if(f>((altura)*40)-10){
                        if( c>=(i*40-2)&&c<=((i+1)*40-5) ){
                           tablero[i][j] =Estado.Punto;
                        }
                    }
                    else{
                        if(TieneHorizontal(i,j+1))
                            tablero[i][j]=Estado.Punto;
                    }
                }

                if(tablero[i][j]==Estado.LasDosInferior){
                    if(c>((ancho)*40)-10){
                        if( f>=(i*40-2)&&f<=((i+1)*40-5) ){
                           tablero[i][j] =Estado.Punto;
                        }
                    }
                    else{
                        if(TieneVertical(i+1,j))
                            tablero[i][j]=Estado.Punto;
                    }
                }                
                if(tablero[i][j]==Estado.BordeDerecho){
                    if(VerticalHorizontal(c,f,i,j)==Estado.Horizontal)
                        tablero[i][j]=Estado.HorizontalDerecho;
                    if(VerticalHorizontal(c,f,i,j)==Estado.Vertical)
                        tablero[i][j]=Estado.VerticalDerecho;
                    if(f>((altura)*40)-10)
                        tablero[i][j]=Estado.InferiorDerecho;
                }
                if(tablero[i][j]==Estado.BordeInferior){
                    if(VerticalHorizontal(c,f,i,j)==Estado.Horizontal)
                        tablero[i][j]=Estado.HorizontalInferior;
                    if(VerticalHorizontal(c,f,i,j)==Estado.Vertical)
                        tablero[i][j]=Estado.VerticalInferior;
                    if(c>((ancho)*40)-10)
                        tablero[i][j]=Estado.InferiorDerecho;                    
                }
                if(tablero[i][j]==Estado.Horizontal){
                    if(VerticalHorizontal(c,f,i,j)==Estado.Vertical)
                        tablero[i][j]=Estado.LasDos;
                    if(c>((ancho)*40)-10)
                        tablero[i][j]=Estado.HorizontalDerecho;
                    if(f>((altura)*40)-10)
                        tablero[i][j]=Estado.HorizontalInferior;
                }
                if(tablero[i][j]==Estado.Vertical){
                    if(VerticalHorizontal(c,f,i,j)==Estado.Horizontal)
                        tablero[i][j]=Estado.LasDos;
                    if(c>((ancho)*40)-10)
                        tablero[i][j]=Estado.VerticalDerecho;
                    if(f>((altura)*40)-10)
                        tablero[i][j]=Estado.VerticalInferior;
                }                   
                if(tablero[i][j]==Estado.LasDos){
                    if(c>((ancho)*40)-10)
                        tablero[i][j]=Estado.LasDosDerecho;
                    if(f>((altura)*40)-10)
                        tablero[i][j]=Estado.LasDosInferior;
                }
            }
            //Mover esre if para que imprima bien el punto
            if(tablero[i][j]==Estado.LasDos||tablero[i][j]== Estado.LasDosDerecho||tablero[i][j]== Estado.LasDosInferior)
                punto(i,j);//Verifica a el final si se hizo punto
            System.out.print(tablero[i][j].ordinal());
         }
         System.out.println();
     }
    if(ganador()==1){
        finJuego=2;
        crearTablero();
    }else{
        finJuego=1;
    }
    System.out.println();
    System.out.println();
    System.out.println();
  }
  
  public Estado VerticalHorizontal(int a,int b, int i, int j){
      //a es x
      //b es y
      Estado Linea = Estado.Blanco;             
      if(a>((i*40)) && a<((i+1)*40)-5){
        if(b>((j*40)) && b<((j+1)*40-33)){
            Linea=Estado.Horizontal;//horizontal
        }
      }
      if(b>((j*40)+5) && b<((j+1)*40-5)){
            if(a>((i*40))&&a<((i+1)*40)-33){
                Linea = Estado.Vertical;//horizontal
            }
      }
      return Linea;               
  } 
  
  public void punto(int a, int b){
      int lineas=0;
      if(tablero[a][b]==Estado.LasDos){
          if(TieneVertical(a+1,b)&&TieneHorizontal(a,b+1)){
              tablero[a][b]=Estado.Punto;
              Puntos1++;
              CasillasLlenas++;
          }
      }
      if(tablero[a][b]==Estado.LasDosDerecho){
          if(TieneHorizontal(a,b+1)){
              tablero[a][b]=Estado.Punto;
              Puntos1++;
              CasillasLlenas++;
          }
      }
      if(tablero[a][b]==Estado.LasDosInferior){
          if(TieneVertical(a+1,b)){
              tablero[a][b]=Estado.Punto;
              Puntos1++;
              CasillasLlenas++;
          }
      }          
  }
    
  public int getCasillasLlenas(){
      return CasillasLlenas;
  }
  
  public int getEstado(){
      return finJuego;
  }
  
  public boolean TieneVertical(int a, int b){
      boolean Tiene=false;
      if(tablero[a][b]==Estado.LasDos)
          Tiene=true;
      if(tablero[a][b]==Estado.LasDosDerecho)
          Tiene=true;      
      if(tablero[a][b]==Estado.LasDosInferior)
          Tiene=true;
      if(tablero[a][b]==Estado.Punto)
          Tiene=true;
      if(tablero[a][b]==Estado.Vertical)
          Tiene=true;   
      if(tablero[a][b]==Estado.VerticalDerecho)
          Tiene=true;
      if(tablero[a][b]==Estado.VerticalInferior)
          Tiene=true;
      return Tiene;
  }

  public boolean TieneHorizontal(int a, int b){
      boolean Tiene=false;
      if(tablero[a][b]==Estado.LasDos)
          Tiene=true;
      if(tablero[a][b]==Estado.LasDosDerecho)
          Tiene=true;      
      if(tablero[a][b]==Estado.LasDosInferior)
          Tiene=true;
      if(tablero[a][b]==Estado.Punto)
          Tiene=true;
      if(tablero[a][b]==Estado.Horizontal)
          Tiene=true;   
      if(tablero[a][b]==Estado.HorizontalDerecho)
          Tiene=true;
      if(tablero[a][b]==Estado.HorizontalInferior)
          Tiene=true;
      return Tiene;
  }  

  public Estado [][] getTablero(){
        if(tablero  == null)
            System.out.println("Esta vacio");
      return tablero;
  }
  
  public int Resultado(){
      return finJuego;
  }
  
  public int ObtenerEstado(int a, int b){
      getArreglo();
      if(tablero[a][b] == null)
          tablero[a][b] = Estado.Blanco;
      int c = tablero[a][b].ordinal();
      return c;
  }
  /*
  /* 
  public int ClickLinea(int boton, int x, int y){
    
      int c = x/40;
      int f = y/40;
      int h =0; // posible valor a retornar y verificar
      int ganador =3;// resive el valor de quien gana el juego
      
      for (int i=1;i < min(ALT,f+2);f2++){
        for (int c2=max(0,c-1);c2 < min(ANC,c+2);c2++){
            if (tablero[f2][c2]!=9){ //Si no es bomba
                tablero[f2][c2]++; //Incrementa el contador
            }
        }
      }
      
      if(casillasLlenas == 49){
          ganador = ganador();
      }
      
      return h;
  }*/
  
  public int ganador(){// regresa un valor de quien gana la partida
      if(CasillasLlenas==36){
        if(Puntos1 == Puntos2)
            return 0;//empate
        else {
            if(Puntos1 > Puntos2)
                return 1;//gano
            else
                return 2;
        }
      }
      return 3;//nada
  }
}