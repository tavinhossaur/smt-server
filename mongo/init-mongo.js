db = db.getSiblingDB('admin');

if (!db.getUser('dev')) {
  db.createUser({
    user: 'dev',
    pwd: 'dev123',
    roles: [{ role: 'root', db: 'admin' }]
  });
  print('Usuário dev criado com sucesso.');
} else {
  print('Usuário dev já existe. Pulando criação.');
}