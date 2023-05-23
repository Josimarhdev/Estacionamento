Estacionamento 

Este é um projeto de software desenvolvido em Java com Spring Boot para gerenciar um estacionamento localizado na cidade de Foz do Iguaçu. Ele permite controlar a entrada e saída de veículos, além de oferecer recursos para cumprir as exigências fiscais.

História de Usuário

Pedro, sócio proprietário do estacionamento, precisa controlar o fluxo de veículos em seu estabelecimento. O sistema deve atender às seguintes necessidades:

- Controlar a entrada e saída dos veículos.
- Aplicar uma política de gratuidade: a cada 50 horas pagas, o veículo recebe 5 horas gratuitas.
- Manter registros dos veículos estacionados, incluindo informações sobre o proprietário.
- Registrar a data e hora da entrada e saída de cada veículo.
- Disponibilizar diferentes tipos de vagas:
  - 50 vagas para carros.
  - 5 vagas para vans.
  - 20 vagas para motos.
- Operar dentro do horário de atendimento: das 06:00 às 20:00 horas.
- Aplicar multa por minuto após o horário de encerramento.

Funcionalidades

O sistema do estacionamento possui as seguintes funcionalidades principais:

- Cadastro de veículos: permite registrar novos veículos no sistema, incluindo informações sobre o proprietário.
- Controle de entrada e saída: registra a data e hora da entrada e saída de cada veículo.
- Verificação de disponibilidade de vagas: indica se há vagas disponíveis para carros, vans ou motos.
- Cálculo de tempo e valor a pagar: calcula o tempo de permanência do veículo no estacionamento e o valor a ser pago, considerando a política de gratuidade.
- Geração de relatórios: fornece relatórios com informações sobre os veículos estacionados, tempo de permanência, valor pago, etc.
- Gerenciamento do horário de atendimento: impede a entrada ou saída de veículos fora do horário de funcionamento estabelecido.
- Cálculo de multa: aplica a multa por minuto aos veículos que excederam o horário de encerramento.

Tecnologias Utilizadas

O projeto foi desenvolvido utilizando as seguintes tecnologias:

- Java
- Spring Boot
- Banco de dados 
- Outras bibliotecas e frameworks conforme necessário

Pré-requisitos

Certifique-se de ter as seguintes ferramentas instaladas em sua máquina:

- Java Development Kit 
- Maven 
- Banco de dados instalado e configurado

Configuração

Siga as etapas abaixo para configurar o projeto em seu ambiente:

1. Clone este repositório para seu dispositivo.
2. Abra o projeto em sua IDE.
3. Configure as credenciais de acesso ao banco de dados no arquivo "application.properties"
