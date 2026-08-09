# notes_service
Сервис управления заметками.

Endpoints:
1) GET http://127.0.0.1:8080/api/notes
   curl --location 'http://127.0.0.1:8080/api/notes'
2) POST http://127.0.0.1:8080/api/notes
   curl --location 'http://127.0.0.1:8080/api/notes' \
   --header 'Content-Type: application/json' \
   --data '{
   "title": "заметка уник тег",
   "content": "что то сделать",
   "tags": [
     "super"
   ]
   }'
3) GET http://127.0.0.1:8080/api/notes/f446c54b-d7d2-4b0a-a204-30c63b336576
   curl --location 'http://127.0.0.1:8080/api/notes/f446c54b-d7d2-4b0a-a204-30c63b336576'
4) GET http://127.0.0.1:8080/api/notes?tag=super
   curl --location 'http://127.0.0.1:8080/api/notes?tag=super'
5) PUT http://127.0.0.1:8080/api/notes/f446c54b-d7d2-4b0a-a204-30c63b336576
   curl --location --request PUT 'http://127.0.0.1:8080/api/notes/f446c54b-d7d2-4b0a-a204-30c63b336576' \
   --header 'Content-Type: application/json' \
   --data '{
   "title": "заметка одын",
   "content": "что то сделать еще",
   "tags": [
   "one",
   "two"
   ]
   }'
6) DELETE http://127.0.0.1:8080/api/notes/eb6c9fd4-6a70-452f-bcce-3a96e735b78e
   curl --location --request DELETE 'http://127.0.0.1:8080/api/notes/b0ab4356-3267-419b-8cc0-ef9a1bbcb62a'
