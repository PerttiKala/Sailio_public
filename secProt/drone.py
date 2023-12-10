### ================================ Base implementation for Tutorial 7 ================= ###
### ====================== Implements Point addition and Scalar Multiplication ========== ###

from dataclasses import dataclass
from re import I
from random import randint
from hashlib import sha256
from Crypto.Cipher import AES
import zerorpc
import time

# In this file I have implemented functionalities for edge drones
# This file can communicate to others using server.py


@dataclass
class PrimeGaloisField:
    prime: int

    def __contains__(self, field_value: "FieldElement") -> bool:
        return 0 <= field_value.value < self.prime


@dataclass
class FieldElement:
    value: int
    field: PrimeGaloisField

    def __repr__(self):
        return "0x" + f"{self.value:x}".zfill(64)

    @property
    def P(self) -> int:
        return self.field.prime

    def __add__(self, other: "FieldElement") -> "FieldElement":
        return FieldElement(
            value=(self.value + other.value) % self.P,
            field=self.field
        )

    def __sub__(self, other: "FieldElement") -> "FieldElement":
        return FieldElement(
            value=(self.value - other.value) % self.P,
            field=self.field
        )

    def __rmul__(self, scalar: int) -> "FieldValue":
        return FieldElement(
            value=(self.value * scalar) % self.P,
            field=self.field
        )

    def __mul__(self, other: "FieldElement") -> "FieldElement":
        return FieldElement(
            value=(self.value * other.value) % self.P,
            field=self.field
        )

    def __pow__(self, exponent: int) -> "FieldElement":
        return FieldElement(
            value=pow(self.value, exponent, self.P),
            field=self.field
        )

    def __truediv__(self, other: "FieldElement") -> "FieldElement":
        other_inv = other ** -1
        return self * other_inv


@dataclass
class EllipticCurve:
    a: int
    b: int

    field: PrimeGaloisField

    def __contains__(self, point: "ECCPoint") -> bool:
        x, y = point.x, point.y
        return y ** 2 == x ** 3 + self.a * x + self.b

    def __post_init__(self):
        # Encapsulate the int parameters in FieldElement
        self.a = FieldElement(self.a, self.field)
        self.b = FieldElement(self.b, self.field)

        # Whether the members of the curve parameters are in the field
        if self.a not in self.field or self.b not in self.field:
            raise ValueError


inf = float("inf")


# Representing an ECC Point using the curve equation yˆ2 = xˆ3 + ax + b
@dataclass
class ECCPoint:
    x: int
    y: int

    curve: EllipticCurve

    def __post_init__(self):
        if self.x is None and self.y is None:
            return

        # Encapsulate x and y in FieldElement
        self.x = FieldElement(self.x, self.curve.field)
        self.y = FieldElement(self.y, self.curve.field)

        # Ensure the ECCPoint satisfies the curve equation
        if self not in self.curve:
            raise ValueError

    ##  ======== Point addition P1 + P2 = P3 ============== ##
    def __add__(self, other):
        if self == I:  # I + P2 = P2
            return other

        if other == I:
            return self  # P1 + I = P1

        if self.x == other.x and self.y == (-1 * other.y):
            return I  # P + (-P) = I

        if self.x != other.x:
            x1, x2 = self.x, other.x
            y1, y2 = self.y, other.y

            out = (y2 - y1) / (x2 - x1)
            x3 = out ** 2 - x1 - x2
            y3 = out * (x1 - x3) - y1

            return self.__class__(
                x=x3.value,
                y=y3.value,
                curve=curve256k1
            )

        if self == other and self.y == inf:
            return I

        if self == other:
            x1, y1, a = self.x, self.y, self.curve.a

            out = (3 * x1 ** 2 + a) / (2 * y1)
            x3 = out ** 2 - 2 * x1
            y3 = out * (x1 - x3) - y1

            return self.__class__(
                x=x3.value,
                y=y3.value,
                curve=curve256k1
            )

    ##  ======== Scalar Multiplication x * P1 = P1 ============== ##
    def __rmul__(self, scalar: int) -> "ECCPoint":
        inPoint = self
        outPoint = I

        while scalar:
            if scalar & 1:
                outPoint = outPoint + inPoint
            inPoint = inPoint + inPoint
            scalar >>= 1
        return outPoint


# Using the secp256k1 elliptic curve equation: yˆ2 = xˆ3 + 7
# Prime of the finite field
# Necessary parameters for the cryptographic operations
P: int = (
    0xFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFC2F
)

field = PrimeGaloisField(prime=P)

A: int = 0
B: int = 7

curve256k1 = EllipticCurve(
    a=A,
    b=B,
    field=field
)

I = ECCPoint(x=None, y=None, curve=curve256k1)  # where I is a point at Infinity

# Generator point of the chosen group
G = ECCPoint(
    x=0x79BE667EF9DCBBAC55A06295CE870B07029BFCDB2DCE28D959F2815B16F81798,
    y=0x483ADA7726A3C4655DA4FBFC0E1108A8FD17B448A68554199C47D08FFB10D4B8,
    curve=curve256k1
)

# Order of the group generated by G, such that nG = I
q = 0xFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEBAAEDCE6AF48A03BBFD25E8CD0364141


def create_ecc_point(x, y):
    x_hex = int(x, 16)
    y_hex = int(y, 16)
    point = ECCPoint(
        x=x_hex,
        y=y_hex,
        curve=curve256k1
    )
    return point

c = zerorpc.Client()
c.connect("tcp://127.0.0.1:4242")
x = c.get_P_pub_x()
y = c.get_P_pub_y()

x_hex = int(x, 16)
y_hex = int(y, 16)

P_pub = ECCPoint(
    x=x_hex,
    y=y_hex,
    curve=curve256k1
)

id = c.get_id()

# ----- GenSecretValue ------
# We also measure times for GenSecValue and ECC multiplication
start_time_genSec = time.time()
x_self = randint(1, q)

start_time_ecc = time.time()
P_self = x_self * G
print("ECC multiplication done in %s seconds" % (time.time() - start_time_ecc))
print("GenSecretValue done in %s seconds" % (time.time() - start_time_genSec))
c.send_P_key(str(P_self.x), str(P_self.y), id)


# ----- FullKeyGen -----

s_i = False
while not s_i:
    s_i = c.get_partial_sec(id)

s_i = int(s_i)

R_i = False
while not R_i:
    R_i = c.get_partial_pub(id)

R_i = create_ecc_point(R_i[0], R_i[1])

sk = s_i + x_hex
pk_i = R_i + P_self

c.send_public_key(str(pk_i.x), str(pk_i.y), id)
print("FullKeyGen successful!!!")


# ----- Re-Key ---------

params = False
while not params:
    params = c.get_cipher(id)

V = params[1]
V = create_ecc_point(V[0], V[1])

cipher = int(params[0])

# Now that we have received parameters from the server, we can start measuring time for KeyRetrieval
start_time_reKey = time.time()

pk_leader = R_i + P_self

T_i = (s_i + x_self) * V
hash1 = int(sha256((str(V + T_i) + "leader_1" + str(id) + str(pk_leader + pk_i)).encode('utf-8')).hexdigest(), 16)
k_g = cipher ^ hash1
k_g = k_g.to_bytes(32, byteorder='big')

print("Re-key done in %s seconds" % (time.time() - start_time_reKey))

command = ""
while command != "q":
    command = input("Give/read a message or quit: ")
    if command == "q":
        break

    elif command == "r":
        msg = c.get_msg()
        cipher_enc = AES.new(k_g, AES.MODE_EAX, nonce=msg[1])
        plaintext = (cipher_enc.decrypt(msg[0])).decode('utf-8')
        print(plaintext)

    else:
        data = bytes(command, 'utf-8')
        cipher = AES.new(k_g, AES.MODE_EAX)
        nonce = cipher.nonce
        ciphertext, tag = cipher.encrypt_and_digest(data)
        c.send_message(ciphertext, nonce)



