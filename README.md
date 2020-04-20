# pardal-client
Repositório de códigos do client do projeto Pardal.

# Instruções para execução
Após receber as instruções e credenciais de acesso, basta mudar os parâmetros *spring.kafka.bootstrap-servers*, *pardal.client.topic-name*, *pardal.client.consumer-username* e *pardal.client.consumer-password*. Em seguida compile o projeto e, via linha de comando, execute a seguinte instrução: _java -jar -Dpardal.client.diretorio=c:/temp/pardal_client/psc/ pardal-client-1.0.0.jar_. O valor do parâmetro *-Dpardal.client.diretorio* precisa ser um caminho em que o usuário tenha permissão de escrita.
