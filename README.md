
# Sudoku

Um jogo de Sudoku em console desenvolvido em Java, com uma implementação robusta e interativa.

---

## Sobre o Projeto

Este é um projeto de jogo de Sudoku para console, implementado em Java, que demonstra o uso de estruturas de dados modernas e tratamento de exceções para garantir a estabilidade do programa. A configuração inicial do tabuleiro é passada como argumentos de linha de comando. O jogo permite ao usuário interagir para preencher e remover números, verificar o status e a integridade do tabuleiro, e finalizar o jogo.

---

## Destaques da Implementação

- **Uso de Map para Configuração Inicial**  
  O jogo utiliza um `Map<String, String>` para armazenar e processar os argumentos de linha de comando que definem a configuração inicial do tabuleiro. As chaves do mapa representam as coordenadas da célula (ex: `"0,0"`) e os valores contêm a configuração (ex: `"5,true"`).

- **Uso de List para Representação do Tabuleiro**  
  O tabuleiro é representado por uma `List` de `List` de objetos `Space`, permitindo uma estrutura bidimensional flexível e a fácil manipulação de cada célula.

- **Tratamento de Exceções (`try-catch`)**  
  O código demonstra a captura de `InputMismatchException` ao ler entradas do usuário, garantindo que apenas inteiros válidos sejam processados. Embora o tratamento não seja abrangente em todas as partes do código fornecido, há uma tentativa de lidar com entradas incorretas do usuário.

---

## Como Executar

Para rodar este projeto, você precisará de um ambiente de desenvolvimento Java (JDK) instalado.

### Compilando

Navegue até a raiz do projeto e compile os arquivos `.java`:

```bash
javac Game/Main.java Game/model/*.java
```

### Executando

Para iniciar o jogo, você deve passar a configuração inicial do tabuleiro como argumentos de linha de comando. Cada argumento representa uma célula do tabuleiro no formato:

```bash
col,row;valor,booleano
```

#### Exemplo:

```bash
java Game.Main 0,0;5,true 0,1;3,false 0,2;...
```

---

## Menu Interativo

Após a execução, o programa apresentará um menu interativo com as seguintes opções:

- **Iniciar um novo jogo**: O jogo é iniciado com as posições fornecidas.
- **Colocar um novo número**: Insere um valor em uma posição específica.
- **Remover um número**: Limpa o valor de uma posição específica.
- **Visualizar jogo atual**: Mostra a representação visual do tabuleiro.
- **Verificar status do jogo**: Informa se o jogo está completo, incompleto ou se contém erros.
- **Limpar jogo**: Limpa todos os números não fixos do tabuleiro.
- **Finalizar jogo**: Verifica se o jogo foi concluído com sucesso.
- **Sair**: Encerra o programa.

---

## Estrutura do Código

O projeto é composto por quatro classes principais:

- **`Main.java`**  
  Contém a lógica principal do jogo, o menu de interação com o usuário e a leitura da entrada. É aqui que o processamento dos argumentos de linha de comando (`Map`) e o tratamento de exceções de entrada (`try-catch`) são implementados.

- **`Board.java`**  
  Representa a grade do Sudoku usando uma `List` de `List` de objetos `Space`. Contém a lógica para manipular o tabuleiro e verificar o estado do jogo.

- **`Space.java`**  
  Representa uma única célula do tabuleiro. Contém o valor atual, o valor esperado e se a célula é fixa.

- **`GameStatus.java`**  
  Um `enum` que define os possíveis estados do jogo (`NON_STARTED`, `INCOMPLETE`, `COMPLETE`). Cada estado tem uma *label* associada.
