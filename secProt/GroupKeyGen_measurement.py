### ================================ Base implementation for Tutorial 7 ================= ###
### ====================== Implements Point addition and Scalar Multiplication ========== ###

from dataclasses import dataclass
from re import I
from random import randint
from random import choice
from hashlib import sha256
from time import time

# This program is for measuring times for GroupKeyGen and Re-Key
# Functionalities for those start at line 230

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

# ==================== Start your implementation below this line ============================== ##
# ==================== Feel free to pull the parameters into another file if you wish ========= ##
# ==================== If you notice any bugs, kindly draw our attention to it ================ ##


# Kgc master secret key and public key (KGC computes)
x_kgc = randint(1, q)
P_pub = G.__rmul__(x_kgc)

# secret values and partial public keys for drones (drones compute)
x_1 = randint(1, q)
x_2 = randint(1, q)

P_1 = G.__rmul__(x_1)
P_2 = G.__rmul__(x_2)

# partial private keys and partial public keys (KGC computes)
r_1 = randint(1, q)
r_2 = randint(1, q)

R_1 = G.__rmul__(r_1)
R_2 = G.__rmul__(r_2)

s_1 = r_1 + x_kgc * int(sha256(("drone_1" + str(R_1 + P_1)).encode('utf-8')).hexdigest(), 16) # CHANGE TO SHA256!!!
s_2 = r_2 + x_kgc * int(sha256(("drone_2" + str(R_2 + P_2)).encode('utf-8')).hexdigest(), 16)

# full private keys and full public keys (drones compute)

sk_1 = s_1 + x_1
pk_1 = R_1 + P_1

sk_2 = s_2 + x_2
pk_2 = R_2 + P_2


# ----------- START OF ENCRYPTION --------------

# For test purposes we ask amount of drones. If old_drones == 0, we are measuring GroupKeyGen time,
# otherwise Re-Key time

new_drones = int(input("Give amount of new drones: "))
old_drones = int(input("Give amount of old drones: "))

# Creates a list containing 100 random ECCPoints that represent keys
keys = []
for i in range(0, 10):
        ecc_point = G.__rmul__(randint(1, q))
        keys.append(ecc_point)

start_time_GkeyGen = time()

# Team leader computes:
k_g = randint(1, q)
l_k = randint(1, q)
V = G.__rmul__(l_k)


switch_to_KeyGen = False

# for each drone:
for i in range(0, (new_drones + old_drones)):

    if i == old_drones:
        switch_to_KeyGen = True

    start = time()
    P_n = choice(keys)
    R_n = choice(keys)
    Pk_n = choice(keys)

    if switch_to_KeyGen:
        pk_1_leader = R_n + P_n
        Y_1 = R_n + P_pub.__rmul__(int(sha256((str(i) + str(R_n + P_n)).encode('utf-8')).hexdigest(), 16)) + P_n
        T_1 = Y_1.__rmul__(l_k)

    else:
        T_1 = choice(keys)
        pk_1_leader = choice(keys)

    hash1 = int(sha256((str(V + T_1) + "leader_1" + str(i) + str(pk_1_leader + Pk_n)).encode('utf-8')).hexdigest(), 16)
    c_n = k_g ^ hash1

if old_drones == 0:
    print("GenGroupKey executed in %s seconds" % (time() - start_time_GkeyGen))
else:
    print("ReKey executed in %s seconds" % (time() - start_time_GkeyGen))


