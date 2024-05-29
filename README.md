# TP-Intergiciel-et-programmation-par-composants
| ![Amina JALIDY](https://avatars.githubusercontent.com/u/103306906?v=4)  | ![Khady GAYE](https://avatars.githubusercontent.com/u/131003251?v=4)          | ![Rania Badi](https://avatars.githubusercontent.com/u/141785971?v=4) |
| :--------------: | :--------------: | :--------------: |
| Amina JALIDY | Khady GAYE        | Rania BADI  |
| [@aminajalidy](https://github.com/aminajalidy) | [@Khady71](https://github.com/Khady71) | [@raniabadi](https://github.com/raniabadi)  |
| amina.jalidy@uphf.fr  | khady.gaye@uphf.fr           | rania.badi@uphf.fr  |

# Technologies utilisées
<a href="https://kafka.apache.org/" target="_blank" rel="noreferrer"> <img src="https://imgs.search.brave.com/vUNX5vHj053oH8GdZXva9X8byPP-0OQMCLXSgv3rLtU/rs:fit:500:0:0/g:ce/aHR0cHM6Ly9zdGF0/aWMud2lraWEubm9j/b29raWUubmV0L2xv/Z29wZWRpYS9pbWFn/ZXMvZC9kOC9BcGFj/aGVfS2Fma2FfTG9n/by5qcGcvcmV2aXNp/b24vbGF0ZXN0L3Nj/YWxlLXRvLXdpZHRo/LWRvd24vMzAwP2Ni/PTIwMjIwNzAzMDIz/NjEz.jpeg" alt="kafka" width="260" height="100"/> </a> <a href="https://www.docker.com/" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/docker/docker-original-wordmark.svg" alt="docker" width="100" height="100"/> </a> <a href="https://maven.apache.org/" target="_blank" rel="noreferrer"><img src="https://raw.githubusercontent.com/vscode-icons/vscode-icons/master/icons/file_type_maven.svg" alt="maven" width="100" height="100"/></a>

# Description du projet- Kafka Sécurisé via SSL

Ce projet consiste à développer une application Kafka sécurisée via SSL, utilisant Maven et Java. Le projet inclut un producteur et un consommateur Kafka, qui démontrent le bon fonctionnement d'un broker Kafka sécurisé.

# Architecture
<img src="https://github.com/aminajalidy/TP-Intergiciel-et-programmation-par-composants/assets/103306906/84d67470-024a-4129-ac4c-59cfd0b77479" alt="Docker Logo" width="550"/>

Cette vue simplifiée du projet montre comment le producteur et le consommateur se connectent au broker Kafka sécurisé via SSL. Le producteur envoie des messages au broker Kafka, et le consommateur lit les messages du broker Kafka, le tout via une connexion sécurisée SSL.

<img src="https://github.com/aminajalidy/TP-Intergiciel-et-programmation-par-composants/assets/103306906/a81e56b0-64ef-4098-8e3b-5701149ffe9d" alt="Docker Logo" width="550"/>

Cette illustration montre la répartition des messages envoyés par le producteur sur différentes partitions du topic Kafka. Chaque partition est consommée par un consommateur distinct, permettant une mise à l'échelle et une distribution équilibrée des messages.


# Fonctionnalités principales

- Sécurisation SSL : Toutes les communications entre le producteur, le consommateur et le broker Kafka sont sécurisées via SSL.
- Gestion des partitions : Le producteur envoie les messages de manière équitable à travers plusieurs partitions, et les consommateurs   peuvent être configurés pour lire à partir de partitions spécifiques.
- Configuration Docker : Utilisation de Docker et Docker Compose pour faciliter le déploiement et la gestion des services Kafka et Zookeeper.
- Support multi-consommateurs : Ajout facile de nouveaux consommateurs qui peuvent lire des partitions spécifiques selon les besoins.

