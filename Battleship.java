import java.util.Scanner;
import java.util.Random;
public class Battleship{

  public static void main(String[] args) {

    Scanner in = new Scanner(System.in);
    int [][] board = new int [10][10];
    int [][] eboard = new int [10][10];
    int [] barcos = new int [5]; //cantidad de puntos hundibles en el mapa
    int m = 0;
    int n = 0;
    //0 = nada ahí
    //1 = barco
    //2 = barco tocado
    //3 = disparo vacío
    //board = tu panel donde estan tus Barcos
    //eboard = board enemigo donde estan sus barcos
    barcos = BarcosArray(barcos);
    board = CreateShips(board, barcos);
    eboard = CreateEnemyShips(eboard, barcos);
    PrintEnemyBoard(eboard);
    while (true){
      eboard = Fire(eboard);
      m = Actualizar(eboard, barcos); //barcos enemigos caídos
      System.out.println();
      System.out.println();
      board = EnemyFire(board);
      n = Actualizar(board, barcos); //barcos aliados caídos
      if (m >= barcos[0]+barcos[1]+barcos[2]+barcos[3]+barcos[4] || n >= barcos[0]+barcos[1]+barcos[2]+barcos[3]+barcos[4]) { break; }
    }
    if (m > n) { System.out.println("LO SENTIMOS, HA GANADO EL ENEMIGO!!"); PrintBoard(board); }
    else {
      if (m < n) { System.out.println("FELICIDADES, HAS GANADO!!"); PrintEnemyBoard(eboard); }
      else{ System.out.println("HA SIDO UN EMPATE, FIN DEL JUEGO!!"); }
    }
  }

  public static int [] BarcosArray(int [] barcos){
    barcos[0] = 5;
    barcos[1] = 4;
    barcos[2] = 3;
    barcos[3] = 3;
    barcos[4] = 2;
    return barcos;
  }

  public static int [][] CreateShips(int [][] board, int [] barcos){
    Scanner in = new Scanner(System.in);
    int l = 0;
    int d = 0;
    int h = 0;
    int x = 0;
    int y = 0;
    int [] posibilidades = new int [4];
    PrintBoard(board);
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
      }
      y = Integer.parseInt(coordenada[1]);
      if (board[y-1][x-1] == 0) {
        board[y-1][x-1]=1;
        PrintBoard(board);
        System.out.println();
        if ((x+l-1)<=board.length) { posibilidades [0] = 1; }
        if ((y-l)>=0) { posibilidades [1] = 1; }
        if ((x-l)>=0) { posibilidades [2] = 1; }
        if ((y+l-1)<=board.length) { posibilidades [3] = 1; }
        d = GetD(posibilidades, d);
        switch (d) {
          case 1: for (int i = 0; i<l; i++) { board[y-1][x+i-1] = 1; }  break;
          case 2: for (int i = 0; i<l; i++) { board[y-i-1][x-1] = 1; } break;
          case 3: for (int i = 0; i<l; i++) { board[y-1][x-i-1] = 1; } break;
          case 4: for (int i = 0; i<l; i++) { board[y+i-1][x-1] = 1; } break;
        }
        PrintBoard(board);
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
    int [] posibilidades = new int [4];
    while (true&&h<barcos.length) {
      l = barcos[h];
      int y = random.nextInt((eboard.length - 1) + 1) + 1;
      int x = random.nextInt((eboard.length - 1) + 1) + 1;
      if (eboard[y-1][x-1] == 0) {
        eboard[y-1][x-1]=1;
        if ((x+l-1)<=eboard.length) { posibilidades [0] = 1; }
        if ((y-l)>=0) { posibilidades [1] = 1; }
        if ((x-l)>=0) { posibilidades [2] = 1; }
        if ((y+l-1)<=eboard.length) { posibilidades [3] = 1; }
        d = GetEnemyD(posibilidades, d);
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

  public static int [][] Fire(int [][] eboard){
    Scanner in = new Scanner(System.in);
    PrintEnemyBoard(eboard);
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
      y = Integer.parseInt(coordenada[1]);
      switch (eboard[y-1][x-1]) {
        case 0: eboard[y-1][x-1] = 3; break;
        case 1: eboard[y-1][x-1] = 2; break;
        case 2: System.out.println("Espacio ocupado, escoge otro sitio"); continue;
      }
      PrintEnemyBoard(eboard);
      break;
    }
    return eboard;
  }

  public static int [][] EnemyFire(int [][] board){
    Random random = new Random();
    while (true) {
      int y = random.nextInt(((board.length-1) - 0) + 1) + 0;
      int x = random.nextInt(((board.length-1) - 0) + 1) + 0;
      switch (board[y][x]) {
        case 0: board[y][x] = 3; break;
        case 1: board[y][x] = 2; break;
        case 2: continue;
      }
      break;
    }
    PrintBoard(board);
    return board;
  }

  public static void PrintBoard(int [][] board){
    System.out.println(" * = Barco Tuyo ");
    System.out.println(" X = Barco Tuyo Tocado ");
    System.out.println(" = = Disapro enemigo vacio ");
    for (int a = 0; a<board.length; a++) {
      System.out.println();
      if (a == 0) {
        System.out.println("      A    B    C    D    E    F    G    H    I    J    ");
        System.out.print(a+1 + "  |");
      }
      else{
        System.out.print(a+1 + "  |");
      }
      for (int b = 0; b<board.length; b++) {
        switch (board[a][b]) {
          case 0: System.out.print("  " + " " + " |"); break;
          case 1: System.out.print("  " + "*" + " |"); break;
          case 2: System.out.print("  " + "X" + " |"); break;
          case 3: System.out.print("  " + "=" + " |"); break;
        }
      }
    }
  }

  public static void PrintEnemyBoard(int [][] eboard){
    System.out.println(" = = Disapro vacio ");
    System.out.println(" X = Barco Enemigo Tocado ");
    for (int a = 0; a<eboard.length; a++) {
      System.out.println();
      if (a == 0) {
        System.out.println("      A    B    C    D    E    F    G    H    I    J    ");
        System.out.print(a+1 + "  |");
      }
      else{
        System.out.print(a+1 + "  |");
      }
      for (int b = 0; b<eboard.length; b++) {
        switch (eboard[a][b]) {
          case 0: System.out.print("  " + " " + " |"); break;
          case 1: System.out.print("  " + " " + " |"); break;
          case 2: System.out.print("  " + "X" + " |"); break;
          case 3: System.out.print("  " + "=" + " |"); break;
        }
      }
    }
  }

  public static int Actualizar(int [][] eboard, int [] barcos){
    int m = 0;
    for (int a = 0; a<eboard.length; a++) {
      for (int b = 0; b<eboard.length; b++) {
        if (eboard[a][b] == 2) { m++; }
      }
    }
    return m;
  }

  public static int GetD(int [] posibilidades, int d){
    Scanner in = new Scanner(System.in);
    if (posibilidades[0]==1) {
      if (posibilidades[1]==1) {
        if (posibilidades[2]==1) {
          if (posibilidades[3]==1) {
            while (true) {
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
          }
          else{
            while (true) {
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
          }
        }
        else {
          if (posibilidades[3]==1) {
            while (true) {
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
          }
          else{
            while (true) {
              System.out.println("Escoge una direccion (1=derecha, 2=arriba)");
              d = in.nextInt();
              switch (d) {
                case 1: d = 1; break;
                case 2: d = 2; break;
                default: System.out.println("Selecciona una dirección válida"); continue;
              }
              break;
            }
          }
        }
      }
      else {
        if (posibilidades[2]==1) {
          if (posibilidades[3]==1) {
            while (true) {
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
          }
          else{
            while (true) {
              System.out.println("Escoge una direccion (1=derecha, 3=izquierda)");
              d = in.nextInt();
              switch (d) {
                case 1: d = 1; break;
                case 3: d = 3; break;
                default: System.out.println("Selecciona una dirección válida"); continue;
              }
              break;
            }
          }
        }
        else{
          if (posibilidades[3]==1) {
            while (true) {
              System.out.println("Escoge una direccion (1=derecha, 4=abajo)");
              d = in.nextInt();
              switch (d) {
                case 1: d = 1; break;
                case 4: d = 4; break;
                default: System.out.println("Selecciona una dirección válida"); continue;
              }
              break;
            }
          }
          else{
            while (true) {
              System.out.println("Escoge una direccion (1=derecha)");
              d = in.nextInt();
              switch (d) {
                case 1: d = 1; break;
                default: System.out.println("Selecciona una dirección válida"); continue;
              }
              break;
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
          }
          else{
            while (true) {
              System.out.println("Escoge una direccion (2=arriba, 3=izquierda)");
              d = in.nextInt();
              switch (d) {
                case 2: d = 2; break;
                case 3: d = 3; break;
                default: System.out.println("Selecciona una dirección válida"); continue;
              }
              break;
            }
          }
        }
        else{
          if (posibilidades[3]==1) {
            while (true) {
              System.out.println("Escoge una direccion (2=arriba, 4=abajo)");
              d = in.nextInt();
              switch (d) {
                case 2: d = 2; break;
                case 4: d = 4; break;
                default: System.out.println("Selecciona una dirección válida"); continue;
              }
              break;
            }
          }
          else{
            while (true) {
              System.out.println("Escoge una direccion (2=arriba)");
              d = in.nextInt();
              switch (d) {
                case 2: d = 2; break;
                default: System.out.println("Selecciona una dirección válida"); continue;
              }
              break;
            }
          }
        }
      }
      else{
        if (posibilidades[2]==1) {
          if (posibilidades[3]==1) {
            while (true) {
              System.out.println("Escoge una direccion (3=izquierda, 4=abajo)");
              d = in.nextInt();
              switch (d) {
                case 3: d = 3; break;
                case 4: d = 4; break;
                default: System.out.println("Selecciona una dirección válida"); continue;
              }
              break;
            }
          }
          else{
            while (true) {
              System.out.println("Escoge una direccion (3=izquierda)");
              d = in.nextInt();
              switch (d) {
                case 1: d = 1; break;
                case 2: d = 2; break;
                case 3: d = 3; break;
                default: System.out.println("Selecciona una dirección válida"); continue;
              }
              break;
            }
          }
        }
        else{
          if (posibilidades[3]==1) {
            while (true) {
              System.out.println("Escoge una direccion (4=abajo)");
              d = in.nextInt();
              switch (d) {
                case 4: d = 4; break;
                default: System.out.println("Selecciona una dirección válida"); continue;
              }
              break;
            }
          }
          else{
            System.out.println("Matriz muy pequeña para jugar");
          }
        }
      }
    }
    return d;
  }

  public static int GetEnemyD(int [] posibilidades, int d){
    Random random = new Random();
    if (posibilidades[0]==1) {
      if (posibilidades[1]==1) {
        if (posibilidades[2]==1) {
          if (posibilidades[3]==1) {
            while (true) {
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
          else{
            while (true) {
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
        else {
          if (posibilidades[3]==1) {
            while (true) {
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
          else{
            while (true) {
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
      else {
        if (posibilidades[2]==1) {
          if (posibilidades[3]==1) {
            while (true) {
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
          else{
            while (true) {
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
        else{
          if (posibilidades[3]==1) {
            while (true) {
              d = random.nextInt((4 - 1) + 1) + 1;
              switch (d) {
                case 1: d = 1; break;
                case 4: d = 4; break;
                default: continue;
              }
              break;
            }
          }
          else{
            while (true) {
              d = random.nextInt((4 - 1) + 1) + 1;
              switch (d) {
                case 1: d = 1; break;
                default: continue;
              }
              break;
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
          else{
            while (true) {
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
        else{
          if (posibilidades[3]==1) {
            while (true) {
              d = random.nextInt((4 - 1) + 1) + 1;
              switch (d) {
                case 2: d = 2; break;
                case 4: d = 4; break;
                default: continue;
              }
              break;
            }
          }
          else{
            while (true) {
              d = random.nextInt((4 - 1) + 1) + 1;
              switch (d) {
                case 2: d = 2; break;
                default: continue;
              }
              break;
            }
          }
        }
      }
      else{
        if (posibilidades[2]==1) {
          if (posibilidades[3]==1) {
            while (true) {
              d = random.nextInt((4 - 1) + 1) + 1;
              switch (d) {
                case 3: d = 3; break;
                case 4: d = 4; break;
                default: continue;
              }
              break;
            }
          }
          else{
            while (true) {
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
        else{
          if (posibilidades[3]==1) {
            while (true) {
              d = random.nextInt((4 - 1) + 1) + 1;
              switch (d) {
                case 4: d = 4; break;
                default: continue;
              }
              break;
            }
          }
          else{
            System.out.println("Matriz muy pequeña para jugar");
          }
        }
      }
    }
    return d;
  }

}
