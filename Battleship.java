import java.util.Scanner;
import java.util.Random;
public class Battleship{

  public static void main(String[] args) {

    Scanner in = new Scanner(System.in);
    int [][] board = new int [10][10];
    int [][] eboard = new int [10][10];
    int [] barcos = new int [5]; //cantidad de puntos hundibles en el mapa
      barcos[0] = 5;
      barcos[1] = 4;
      barcos[2] = 3;
      barcos[3] = 3;
      barcos[4] = 2;
    int m = 0; //barcos enemigos caidos
    int n = 0; //barcos aliados caidos
    boolean enemigo = true;
    //0 = nada ahí
    //1 = barco
    //2 = barco tocado
    //3 = disparo vacío
    //board = tu panel donde estan tus Barcos
    //eboard = board enemigo donde estan sus barcos
    board = CreateShips(board, barcos);
    eboard = CreateEnemyShips(eboard, barcos);
    PrintBoard(eboard, enemigo = true);
    while (true){
      eboard = Fire(eboard, enemigo = false);
      m = Actualizar(eboard, barcos); //barcos enemigos caídos
      System.out.println();
      System.out.println();
      board = Fire(board, enemigo = true);
      PrintBoard(board, enemigo = false);
      PrintBoard(eboard, enemigo = true);
      n = Actualizar(board, barcos); //barcos aliados caídos
      if (m >= barcos[0]+barcos[1]+barcos[2]+barcos[3]+barcos[4] || n >= barcos[0]+barcos[1]+barcos[2]+barcos[3]+barcos[4]) { break; }
    }
    if (m > n) { System.out.println("FELICIDADES, HAS GANADO!!"); PrintBoard(eboard, enemigo = true); }
    else {
      if (m < n) { System.out.println("LO SENTIMOS, HA GANADO EL ENEMIGO!!"); PrintBoard(board, enemigo = false); }
      else{ System.out.println("HA SIDO UN EMPATE, FIN DEL JUEGO!!"); }
    }
  }

  public static int [][] CreateShips(int [][] board, int [] barcos){
    Scanner in = new Scanner(System.in);
    int l = 0;
    int h = 0;
    int x = 0;
    int y = 0;
    int [] posibilidades = new int [4];
    boolean enemigo = false;
    PrintBoard(board, enemigo);
    while (true&&h<barcos.length) {
      System.out.println();
      System.out.println("La longitud del siguiente barco es de: " + barcos[h]);
      l = barcos[h]; //l = longitud de los barcos
      System.out.println("Coordenada del barco (Ejemplo: C_4)");
      String coor = in.next();
      String [] coordenada = coor.split("_");
      switch (coordenada[0]) {
        case "A": x = 1; break;
        case "B": x = 2; break;
        case "C": x = 3; break;
        case "D": x = 4; break;
        case "E": x = 5; break;
        case "F": x = 6; break;
        case "G": x = 7; break;
        case "H": x = 8; break;
        case "I": x = 9; break;
        case "J": x = 10; break;
        default: System.out.println("Coordenada mal puesta, favor de repetir"); continue;
      }
      y = Integer.parseInt(coordenada[1]);
      if (board[y-1][x-1] == 0) {
        board[y-1][x-1]=1;
        PrintBoard(board, enemigo);
        System.out.println();
        if ((x+l-1)<=board.length) {
          posibilidades [0] = 1;
          for (int k = 1; k<l; k++) { if (board[y-1][x-1+k] == 1) { posibilidades [0] = 0; } }
        }
        if ((y-l)>=0) {
          posibilidades [1] = 1;
          for (int k = 1; k<l; k++) { if (board[y-1-k][x-1] == 1) { posibilidades [1] = 0; } }
        }
        if ((x-l)>=0) {
          posibilidades [2] = 1;
          for (int k = 1; k<l; k++) { if (board[y-1][x-1-k] == 1) { posibilidades [2] = 0; } }
        }
        if ((y+l-1)<=board.length) {
          posibilidades [3] = 1;
          for (int k = 1; k<l; k++) { if (board[y-1+k][x-1] == 1) { posibilidades [3] = 0; } }
        }
        int d = GetD(posibilidades, enemigo = false);
        switch (d) {
          case 1: for (int i = 0; i<l; i++) { board[y-1][x+i-1] = 1; }  break;
          case 2: for (int i = 0; i<l; i++) { board[y-i-1][x-1] = 1; } break;
          case 3: for (int i = 0; i<l; i++) { board[y-1][x-i-1] = 1; } break;
          case 4: for (int i = 0; i<l; i++) { board[y+i-1][x-1] = 1; } break;
        }
        PrintBoard(board, enemigo);
        for (int c = 0; c<posibilidades.length; c++) {
          posibilidades[c]=0;
        }
      }
      else { System.out.println("Ya tienes un barco colocado ahi"); continue; }
      h++;
    }
    return board;
  }

  public static int [][] CreateEnemyShips(int [][] eboard, int [] barcos){
    Random random = new Random();
    int l = 0;
    int d = 0;
    int h = 0;
    boolean enemigo = true;
    int [] posibilidades = new int [4];
    while (true&&h<barcos.length) {
      l = barcos[h];
      int y = random.nextInt((eboard.length - 1) + 1) + 1;
      int x = random.nextInt((eboard.length - 1) + 1) + 1;
      if (eboard[y-1][x-1] == 0) {
        eboard[y-1][x-1]=1;
        if ((x+l-1)<=eboard.length) {
          posibilidades [0] = 1;
          for (int k = 1; k<l; k++) { if (eboard[y-1][x-1+k] == 1) { posibilidades [0] = 0; } }
        }
        if ((y-l)>=0) {
          posibilidades [1] = 1;
          for (int k = 1; k<l; k++) { if (eboard[y-1-k][x-1] == 1) { posibilidades [1] = 0; } }
        }
        if ((x-l)>=0) {
          posibilidades [2] = 1;
          for (int k = 1; k<l; k++) { if (eboard[y-1][x-1-k] == 1) { posibilidades [2] = 0; } }
        }
        if ((y+l-1)<=eboard.length) {
          posibilidades [3] = 1;
          for (int k = 1; k<l; k++) { if (eboard[y-1+k][x-1] == 1) { posibilidades [3] = 0; } }
        }
        d = GetD(posibilidades, enemigo = true);
        switch (d) {
          case 1: for (int i = 0; i<l; i++) { eboard[y-1][x+i-1] = 1; }  break;
          case 2: for (int i = 0; i<l; i++) { eboard[y-i-1][x-1] = 1; } break;
          case 3: for (int i = 0; i<l; i++) { eboard[y-1][x-i-1] = 1; } break;
          case 4: for (int i = 0; i<l; i++) { eboard[y+i-1][x-1] = 1; } break;
        }
        for (int c = 0; c<posibilidades.length; c++) {
          posibilidades[c]=0;
        }
      }
      else { continue; }
      h++;
    }
    return eboard;
  }

  public static int [][] Fire(int [][] board, boolean enemigo){
    Scanner in = new Scanner(System.in);
    Random random = new Random();
    int [] posibilidades = new int [4];
    if (enemigo == false) {
      while (true) {
        int x = 0;
        int y = 0;
        System.out.println("Coordenada a disparar (Ejemplo: C_4)");
        String coor = in.next();
        String [] coordenada = coor.split("_");
        switch (coordenada[0]) {
          case "A": x = 1; break;
          case "B": x = 2; break;
          case "C": x = 3; break;
          case "D": x = 4; break;
          case "E": x = 5; break;
          case "F": x = 6; break;
          case "G": x = 7; break;
          case "H": x = 8; break;
          case "I": x = 9; break;
          case "J": x = 10; break;
          default: System.out.println("Coordenada mal puesta, favor de repetir:"); continue;
        }
        y = Integer.parseInt(coordenada[1]);
        switch (board[y-1][x-1]) {
          case 0: board[y-1][x-1] = 3; break;
          case 1:
          board[y-1][x-1] = 2;
          System.out.println();
          System.out.println("Golpe acertado!! Otro tiro: ");
          PrintBoard(board, enemigo = true);
          enemigo = false;
          continue;
          case 2: System.out.println("Espacio ocupado, escoge otro sitio"); continue;
          case 3: System.out.println("Ya disparaste ahi, escoge otro sitio"); continue;
        }
        break;
      }
      System.out.println("Has disparado");
    }
    else{
      boolean sospecha = false;
      int y = -1;
      int x = -1;
      int d = 0;
      while (true) {
        if (sospecha == false) {
          x = random.nextInt(((board.length-1) - 0) + 1) + 0;
          y = random.nextInt(((board.length-1) - 0) + 1) + 0;
        }
        else{ //cuando antes ya hubo un golpe
          System.out.println("nuestra coordenada y es " + y);
          System.out.println("nuestra coordenada x es " + x);
          if (d == 0) {
            if (x+1 < board.length) {
              if (board[y][x+1] != 2 && board[y][x+1] != 3) { posibilidades[0]=1; }
            }
            if (y-1>=0) {
              if (board[y-1][x] != 2 && board[y-1][x] != 3) { posibilidades[1]=1; }
            }
            if (x-1>=0) {
              if (board[y][x-1] != 2 && board[y][x-1] != 3) { posibilidades[2]=1; }
            }
            if (y+1 < board.length) {
              if (board[y+1][x] != 2 && board[y+1][x] != 3) { posibilidades[3]=1; }
            }
            d = GetD(posibilidades, enemigo = true);
            for (int c = 0; c<posibilidades.length; c++) { posibilidades[c]=0; }
          }
          switch (d) {
            case 0: break;
            case 1: if (x+1 < board.length) { x = x+1; } break;
            case 2: if (y-1 >= 0) { x = y-1; } break;
            case 3: if (x-1 >= 0) { x = x-1; } break;
            case 4: if (y+1 < board.length) { x = y+1; } break;
          }
        }
        switch (board[y][x]) {
          case 0:
            board[y][x] = 3;
            sospecha = false;
            d = 0;
          break;
          case 1:
            board[y][x] = 2;
            sospecha = true;
          continue;
          case 2: sospecha = false; continue;
          case 3: sospecha = false; continue;
        }
        break;
      }
    }
    return board;
  }

  public static void PrintBoard(int [][] board, boolean enemigo){
    if (enemigo == false) {
      System.out.println();
      System.out.println();
      System.out.println("  T  U     T  A  B  L  E  R  O  ");
      System.out.println();
      System.out.println(" * = Barco Tuyo ");
      System.out.println(" X = Barco Tuyo Tocado ");
      System.out.println(" = = Disapro enemigo vacio ");
    }
    else {
      System.out.println();
      System.out.println();
      System.out.println("  T  A  B  L  E  R  O     E  N  E  M  I  G  O  ");
      System.out.println();
      System.out.println(" X = Barco Enemigo Tocado ");
      System.out.println(" = = Disapro vacio ");
    }
    for (int y = 0; y<board.length; y++) {
      System.out.println();
      if (y == 0) {
        System.out.println("      A    B    C    D    E    F    G    H    I    J    ");
        System.out.print(y+1 + "  |");
      }
      else{
        System.out.print(y+1 + "  |");
      }
      for (int x = 0; x<board.length; x++) {
        switch (board[y][x]) {
          case 0: System.out.print("  " + " " + " |"); break;
          case 1: if (enemigo == false) { System.out.print("  " + "*" + " |"); break; } else { System.out.print("  " + " " + " |"); break; }
          case 2: System.out.print("  " + "X" + " |"); break;
          case 3: System.out.print("  " + "=" + " |"); break;
        }
      }
    }
  }

  public static int Actualizar(int [][] eboard, int [] barcos){
    int m = 0; //barcos caidos
    for (int a = 0; a<eboard.length; a++) {
      for (int b = 0; b<eboard.length; b++) {
        if (eboard[a][b] == 2) { m++; }
      }
    }
    return m;
  }

  public static int GetD(int [] posibilidades,  boolean enemigo){
    Scanner in = new Scanner(System.in);
    Random random = new Random();
    int d = 0;
    if (posibilidades[0]==1) {
      if (posibilidades[1]==1) {
        if (posibilidades[2]==1) {
          if (posibilidades[3]==1) {
            while (true) {
              if (enemigo == false) {
                System.out.println("Escoge una direccion (1=derecha, 2=arriba, 3=izquierda, 4=abajo)");
                d = in.nextInt();
                switch (d) {
                  case 1: d = 1; break;
                  case 2: d = 2; break;
                  case 3: d = 3; break;
                  case 4: d = 4; break;
                  default: System.out.println("Selecciona una dirección válida"); continue;
                }
                break;
              }
              else {
                d = random.nextInt((4 - 1) + 1) + 1;
                switch (d) {
                  case 1: d = 1; break;
                  case 2: d = 2; break;
                  case 3: d = 3; break;
                  case 4: d = 4; break;
                  default: continue;
                }
                break;
              }
            }
          }
          else{
            while (true) {
              if (enemigo == false) {
                System.out.println("Escoge una direccion (1=derecha, 2=arriba, 3=izquierda)");
                d = in.nextInt();
                switch (d) {
                  case 1: d = 1; break;
                  case 2: d = 2; break;
                  case 3: d = 3; break;
                  default: System.out.println("Selecciona una dirección válida"); continue;
                }
                break;
              }
              else {
                d = random.nextInt((4 - 1) + 1) + 1;
                switch (d) {
                  case 1: d = 1; break;
                  case 2: d = 2; break;
                  case 3: d = 3; break;
                  default: continue;
                }
                break;
              }
            }
          }
        }
        else {
          if (posibilidades[3]==1) {
            while (true) {
              if (enemigo == false) {
                System.out.println("Escoge una direccion (1=derecha, 2=arriba, 4=abajo)");
                d = in.nextInt();
                switch (d) {
                  case 1: d = 1; break;
                  case 2: d = 2; break;
                  case 4: d = 4; break;
                  default: System.out.println("Selecciona una dirección válida"); continue;
                }
                break;
              }
              else {
                d = random.nextInt((4 - 1) + 1) + 1;
                switch (d) {
                  case 1: d = 1; break;
                  case 2: d = 2; break;
                  case 4: d = 4; break;
                  default: continue;
                }
                break;
              }
            }
          }
          else{
            while (true) {
              if (enemigo == false) {
                System.out.println("Escoge una direccion (1=derecha, 2=arriba)");
                d = in.nextInt();
                switch (d) {
                  case 1: d = 1; break;
                  case 2: d = 2; break;
                  default: System.out.println("Selecciona una dirección válida"); continue;
                }
                break;
              }
              else {
                d = random.nextInt((4 - 1) + 1) + 1;
                switch (d) {
                  case 1: d = 1; break;
                  case 2: d = 2; break;
                  default: continue;
                }
                break;
              }
            }
          }
        }
      }
      else {
        if (posibilidades[2]==1) {
          if (posibilidades[3]==1) {
            while (true) {
              if (enemigo == false) {
                System.out.println("Escoge una direccion (1=derecha, 3=izquierda, 4=abajo)");
                d = in.nextInt();
                switch (d) {
                  case 1: d = 1; break;
                  case 3: d = 3; break;
                  case 4: d = 4; break;
                  default: System.out.println("Selecciona una dirección válida"); continue;
                }
                break;
              }
              else {
                d = random.nextInt((4 - 1) + 1) + 1;
                switch (d) {
                  case 1: d = 1; break;
                  case 3: d = 3; break;
                  case 4: d = 4; break;
                  default: continue;
                }
                break;
              }
            }
          }
          else{
            while (true) {
              if (enemigo == false) {
                System.out.println("Escoge una direccion (1=derecha, 3=izquierda)");
                d = in.nextInt();
                switch (d) {
                  case 1: d = 1; break;
                  case 3: d = 3; break;
                  default: System.out.println("Selecciona una dirección válida"); continue;
                }
                break;
              }
              else {
                d = random.nextInt((4 - 1) + 1) + 1;
                switch (d) {
                  case 1: d = 1; break;
                  case 3: d = 3; break;
                  default: continue;
                }
                break;
              }
            }
          }
        }
        else{
          if (posibilidades[3]==1) {
            while (true) {
              if (enemigo == false) {
                System.out.println("Escoge una direccion (1=derecha, 4=abajo)");
                d = in.nextInt();
                switch (d) {
                  case 1: d = 1; break;
                  case 4: d = 4; break;
                  default: System.out.println("Selecciona una dirección válida"); continue;
                }
                break;
              }
              else {
                d = random.nextInt((4 - 1) + 1) + 1;
                switch (d) {
                  case 1: d = 1; break;
                  case 4: d = 4; break;
                  default: continue;
                }
                break;
              }
            }
          }
          else{
            while (true) {
              d = 1;
            }
          }
        }
      }
    }
    else {
      if (posibilidades[1]==1) {
        if (posibilidades[2]==1) {
          if (posibilidades[3]==1) {
            while (true) {
              if (enemigo == false) {
                System.out.println("Escoge una direccion (2=arriba, 3=izquierda, 4=abajo)");
                d = in.nextInt();
                switch (d) {
                  case 2: d = 2; break;
                  case 3: d = 3; break;
                  case 4: d = 4; break;
                  default: System.out.println("Selecciona una dirección válida"); continue;
                }
                break;
              }
              else {
                d = random.nextInt((4 - 1) + 1) + 1;
                switch (d) {
                  case 2: d = 2; break;
                  case 3: d = 3; break;
                  case 4: d = 4; break;
                  default: continue;
                }
                break;
              }
            }
          }
          else{
            while (true) {
              if (enemigo == false) {
                System.out.println("Escoge una direccion (2=arriba, 3=izquierda)");
                d = in.nextInt();
                switch (d) {
                  case 2: d = 2; break;
                  case 3: d = 3; break;
                  default: System.out.println("Selecciona una dirección válida"); continue;
                }
                break;
              }
              else {
                d = random.nextInt((4 - 1) + 1) + 1;
                switch (d) {
                  case 2: d = 2; break;
                  case 3: d = 3; break;
                  default: continue;
                }
                break;
              }
            }
          }
        }
        else{
          if (posibilidades[3]==1) {
            while (true) {
              if (enemigo == false) {
                System.out.println("Escoge una direccion (2=arriba, 4=abajo)");
                d = in.nextInt();
                switch (d) {
                  case 2: d = 2; break;
                  case 4: d = 4; break;
                  default: System.out.println("Selecciona una dirección válida"); continue;
                }
                break;
              }
              else {
                d = random.nextInt((4 - 1) + 1) + 1;
                switch (d) {
                  case 2: d = 2; break;
                  case 4: d = 4; break;
                  default: continue;
                }
                break;
              }
            }
          }
          else{
            d = 2;
          }
        }
      }
      else{
        if (posibilidades[2]==1) {
          if (posibilidades[3]==1) {
            while (true) {
              if (enemigo == false) {
                System.out.println("Escoge una direccion (3=izquierda, 4=abajo)");
                d = in.nextInt();
                switch (d) {
                  case 3: d = 3; break;
                  case 4: d = 4; break;
                  default: System.out.println("Selecciona una dirección válida"); continue;
                }
                break;
              }
              else {
                d = random.nextInt((4 - 1) + 1) + 1;
                switch (d) {
                  case 3: d = 3; break;
                  case 4: d = 4; break;
                  default: continue;
                }
                break;
              }
            }
          }
          else{
            d = 3;
          }
        }
        else{
          if (posibilidades[3]==1) {
            d = 4;
          }
          else{
            d = 0;
          }
        }
      }
    }
    return d;
  }

}
