# WorkHub

O **WorkHub** é um projeto pessoal que estou desenvolvendo para organizar minha rotina como desenvolvedor. O objetivo principal é ter um lugar centralizado para guardar códigos, arquivos e lembretes importantes, um "hub" prático para o dia a dia.

Embora o foco inicial seja o meu uso próprio, estou projetando o sistema para possíveis novos usuários futuramente.

## Contexto da motivação

Como dev, é comum espalhar informação por vários lugares: Notion, Obsidian, pastas locais ou até mensagens para nós mesmos e o workhub vem com esse intuíto de centralizar tudo em um só lugar.

## Tecnologias

A ideia é não gastar muita energia com o frontend, tendo em vista que sou backend. Por isso, escolhi uma stack que me permite focar na lógica do backend e na arquitetura sem a necessidade de uma SPA.

- **Mensageria:** RabbitMQ
- **Banco de Dados:** PostgreSQL
- **Frontend:** Thymeleaf + HTMX
- **Backend:** Java 21 com Spring Boot
- **Infraestrutura:** Docker e Kubernetes

## Arquitetura e Conceitos Explorados

Estou usando este projeto também para colocar em prática alguns temas mais avançados:

- **Arquitetura Modular:** Organização limpa das camadas do sistema, optando pela arquitetura hexagonal.
- **Escalabilidade:** Configurações prontas para rodar em Kubernetes.
- **Gestão de Arquivos:** Upload e gerenciamento centralizado de documentos.
- **Autenticação & Usuários:** Sistema completo de login e controle de acesso.
- **Workers & Async:** Processamento de tarefas em segundo plano via mensageria.

## Funcionalidades Planejadas

- [ ] **Dashboard:** Visão geral das tarefas, arquivos e notas.
- [ ] **Tasks/Lembretes:** Lista de tarefas focada no fluxo de trabalho.
- [ ] **Hub de docs:** Um hub para centralizar todas as docs que utilizei.
- [ ] **File Hub:** Repositório simples para arquivos que preciso ter sempre à mão.
- [ ] **Notas Técnicas:** Espaço para snippets, comandos rápidos e anotações de estudo.
- [ ] **Gestão Multi-usuário:** Embora pessoal no momento, o sistema suporta múltiplos perfis.

---
