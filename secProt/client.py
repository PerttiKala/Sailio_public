import zerorpc


c = zerorpc.Client()
c.connect("tcp://192.168.1.112:4242")
print(c.send_leader("kissa"))
print(c.send_edge("koira"))

