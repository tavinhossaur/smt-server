db = db.getSiblingDB("admin");
db.createUser({
  user: "dev",
  pwd: "dev123",
  roles: [
    { role: "root", db: "admin" },
    { role: "readWrite", db: "dev-smt-db" }
  ]
});
