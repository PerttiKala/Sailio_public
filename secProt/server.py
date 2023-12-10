import zerorpc


ids = list(range(1, 10))
system_params = []
p_keys = {}
partial_sec_keys = {}
partial_pub_keys = {}
private_keys = {}
ciphers = {}
msg = []


# This python file makes it possible for drones and KGC to communicate to each other
# In class 'Client' I have implemented methods that participants can use for sending and receiving data

class Client:

	def get_id(self):
		id = ids[0]
		ids.pop(0)
		return id

	def send_cipher(self, cipher, v_x, v_y, id):
		ciphers[id] = (cipher, (v_x, v_y))

	def get_cipher(self, id):
		if id in ciphers.keys():
			return ciphers.get(id)
		else:
			return False

	def send_params(self, param):
		system_params.append(param)

	def get_P_pub_x(self):
		return system_params[0]

	def get_P_pub_y(self):
		return system_params[1]

	def send_P_key(self, x, y, id):
		p_keys[id] = (x, y)

	def get_P_key(self, id):
		if id in p_keys.keys():
			return p_keys.get(id)
		else:
			return False

	def send_partial_pub(self, x, y, id):
		partial_pub_keys[id] = (x, y)
		print("pub partials sent successfully")

	def send_partial_sec(self, key, id):
		partial_sec_keys[id] = (key)
		print("sec partials sent successfully")

	def get_partial_pub(self, id):
		if id in partial_pub_keys.keys():
			return partial_pub_keys[id]
		else:
			return False

	def get_partial_sec(self, id):
		if id in partial_sec_keys.keys():
			return partial_sec_keys[id]
		else:
			return False

	def send_public_key(self, key_x, key_y, id):
		private_keys[id] = (key_x, key_y)

	def get_public_key(self, id):
		if id in private_keys.keys():
			return private_keys[id]
		else:
			return False

	def send_message(self, message, nonce):
		msg.clear()
		msg.append(message)
		msg.append(nonce)
		print(msg)

	def get_msg(self):
		if len(msg) == 0:
			return ""
		return msg


s = zerorpc.Server(Client())
s.bind("tcp://0.0.0.0:4242")
s.run()
