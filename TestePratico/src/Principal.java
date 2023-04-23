import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.HashMap;


public class Principal {

    public static void main(String[] args) {
        
        List<Funcionario> funcionarios = new ArrayList<>();
        
        // Inserindo os funcionários na mesma ordem da tabela
        funcionarios.add(new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"));
        funcionarios.add(new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"));
        funcionarios.add(new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"));
        funcionarios.add(new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"));
        funcionarios.add(new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"));
        funcionarios.add(new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"));
        funcionarios.add(new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"));
        funcionarios.add(new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"));
        funcionarios.add(new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"));
        funcionarios.add(new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente"));        
        
        System.out.println("Funcionários antes da remoção de João:");
        for (Funcionario f : funcionarios) {
            System.out.println(f);
        }
        
        // Removendo o funcionário "João" da lista
        funcionarios.removeIf(f -> f.getNome().equals("João"));
        
        System.out.println();
        System.out.println("Funcionários após a remoção de João:");
        for (Funcionario f : funcionarios) {
            System.out.println(f);
        }
        
        // Formato de data para impressão
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        for (Funcionario f : funcionarios) {
            BigDecimal salarioAtual = f.getSalario();
            BigDecimal aumento = salarioAtual.multiply(BigDecimal.valueOf(0.1)); // calcula 10% de aumento
            BigDecimal novoSalario = salarioAtual.add(aumento); // soma o aumento ao salário atual
            f.setSalario(novoSalario); // atualiza o salário do funcionário na lista
        }
        
        System.out.println();
        // Imprimindo funcionários com informações formatadas
        System.out.printf("%-10s %-15s %-15s %s%n", "Nome", "Data Nascimento", "Salário", "Função");
        for (Funcionario f : funcionarios) {
            System.out.printf("%-10s %-15s %-15s %s%n", f.getNome(), f.getDataNascimento().format(dtf), 
                    f.getSalario().toString().replace(".", ","), f.getFuncao());
        }
        
        Map<String, List<Funcionario>> funcionariosPorFuncao = new HashMap<>();
        for (Funcionario f : funcionarios) {
            String funcao = f.getFuncao();
            List<Funcionario> lista = funcionariosPorFuncao.getOrDefault(funcao, new ArrayList<>());
            lista.add(f);
            funcionariosPorFuncao.put(funcao, lista);
        }
        
        System.out.println();
        for (String funcao : funcionariosPorFuncao.keySet()) {
            System.out.println("Funcionários da função " + funcao + ":");
            for (Funcionario f : funcionariosPorFuncao.get(funcao)) {
                System.out.println("Nome: " + f.getNome() + " | Data de Nascimento: " + f.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " | Salário: " + f.getSalario().setScale(2, RoundingMode.HALF_UP).toString().replace(".", ",") + " | Função: " + f.getFuncao());
            }
            System.out.println();
        }
        
        // Imprimir os funcionários que fazem aniversário no mês 10 e 12
        System.out.println("Funcionários que fazem aniversário em outubro (mês 10) e dezembro (mês 12):");
        for (Funcionario funcionario : funcionarios) {
            int mesNascimento = funcionario.getDataNascimento().getMonthValue();
            if (mesNascimento == 10 || mesNascimento == 12) {
                System.out.println(funcionario.getNome() + " - " + funcionario.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            }
        }
        
        // Encontra o funcionário mais velho
        Funcionario funcionarioMaisVelho = Collections.max(funcionarios, Comparator.comparing(Funcionario::getDataNascimento));

        // Calcula a idade do funcionário
        LocalDate dataAtual = LocalDate.now();
        Period periodo = Period.between(funcionarioMaisVelho.getDataNascimento(), dataAtual);
        int idade = periodo.getYears();

        // Imprime o nome e a idade do funcionário mais velho
        System.out.println();
        System.out.println("Funcionário mais velho:");
        System.out.println("Nome: " + funcionarioMaisVelho.getNome());
        System.out.println("Idade: " + idade + " anos");
        
        // Ordena a lista de funcionários por nome
        Collections.sort(funcionarios, Comparator.comparing(Funcionario::getNome));

        // Imprime a lista de funcionários em ordem alfabética
        System.out.println();
        System.out.println("Lista de funcionários em ordem alfabética:");
        for (Funcionario funcionario : funcionarios) {
            System.out.println("Nome: " + funcionario.getNome() +
                               " | Data de Nascimento: " + funcionario.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +
                               " | Salário: " + NumberFormat.getNumberInstance(Locale.getDefault()).format(funcionario.getSalario()) +
                               " | Função: " + funcionario.getFuncao());
        }
        
        double totalSalarios = funcionarios.stream()
        	    .mapToDouble(Funcionario::getSalarioAsDouble)
        	    .sum();
        System.out.println();
        System.out.printf("Total dos salários dos funcionários: R$ %.2f\n", totalSalarios);
        
        System.out.println();
        System.out.println("Salários em salários mínimos:");
        for(Funcionario funcionario : funcionarios) {
            double salarioMinimos = Math.floor(funcionario.getSalario().doubleValue() / 1212.0);
            System.out.println(funcionario.getNome() + ": " + salarioMinimos + " salários mínimos");
        }    

        
    }

}
