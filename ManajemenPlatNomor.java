// Kelas data plat nomor
class DataPlatNomor {
    String nomorPlat;
    String namaPemilik;

    public DataPlatNomor(String nomorPlat, String namaPemilik) {
        this.nomorPlat = nomorPlat;
        this.namaPemilik = namaPemilik;
    }
}

// Kelas Node untuk Red-Black Tree
class Node {
    DataPlatNomor data;
    Node parent;
    Node left;
    Node right;
    int warna; // 0 untuk hitam, 1 untuk merah

    public Node() {
        this.data = null;
        this.parent = null;
        this.left = null;
        this.right = null;
        this.warna = 1; // Default warna merah
    }
}

// Kelas Manajemen Plat Nomor
public class ManajemenPlatNomor {
    private Node root;
    private Node TNULL;

    public ManajemenPlatNomor() {
        TNULL = new Node();
        TNULL.warna = 0; // Warna hitam untuk daun kosong
        TNULL.left = null;
        TNULL.right = null;
        root = TNULL;
    }

    // Metode penyisipan data plat nomor ke Red-Black Tree
    public void sisip(DataPlatNomor dataPlatNomor) {
        Node node = new Node();
        node.parent = null;
        node.data = dataPlatNomor;
        node.left = TNULL;
        node.right = TNULL;
        node.warna = 1; // Baru disisipkan, warna merah

        Node y = null;
        Node x = this.root;

        while (x != TNULL) {
            y = x;
            if (node.data.nomorPlat.compareTo(x.data.nomorPlat) < 0) {
                x = x.left;
            } else {
                x = x.right;
            }
        }

        node.parent = y;
        if (y == null) {
            root = node;
        } else if (node.data.nomorPlat.compareTo(y.data.nomorPlat) < 0) {
            y.left = node;
        } else {
            y.right = node;
        }

        if (node.parent == null){
            node.warna = 0;
            return;
        }

        if (node.parent.parent == null) {
            return;
        }

        perbaikiSisip(node);
    }

    // Metode pencarian data plat nomor dalam Red-Black Tree
    public DataPlatNomor cari(String nomorPlat) {
        return cariBantuan(this.root, nomorPlat);
    }

    private DataPlatNomor cariBantuan(Node node, String nomorPlat) {
        if (node == TNULL || nomorPlat.equals(node.data.nomorPlat)) {
            return node.data;
        }

        if (nomorPlat.compareTo(node.data.nomorPlat) < 0) {
            return cariBantuan(node.left, nomorPlat);
        }

        return cariBantuan(node.right, nomorPlat);
    }

    // memperbaiki pohon setelah operasi penyisipan
    private void perbaikiSisip(Node k){
        Node u;
        while (k.parent.warna == 1) {
            if (k.parent == k.parent.parent.right) {
                u = k.parent.parent.left; // y adalah saudara k
                if (u.warna == 1) {
                    u.warna = 0;
                    k.parent.warna = 0;
                    k.parent.parent.warna = 1;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.left) {
                        k = k.parent;
                        rotasiKanan(k);
                    }
                    k.parent.warna = 0;
                    k.parent.parent.warna = 1;
                    rotasiKiri(k.parent.parent);
                }
            } else {
                u = k.parent.parent.right; // y adalah saudara k

                if (u.warna == 1) {
                    u.warna = 0;
                    k.parent.warna = 0;
                    k.parent.parent.warna = 1;
                    k = k.parent.parent; // pindah ke atas
                } else {
                    if (k == k.parent.right) {
                        k = k.parent;
                        rotasiKiri(k);
                    }
                    k.parent.warna = 0;
                    k.parent.parent.warna = 1;
                    rotasiKanan(k.parent.parent);
                }
            }
            if (k == root) {
                break;
            }
        }
        root.warna = 0;
    }

    // Metode rotasi ke kanan
    private void rotasiKanan(Node x){
        Node y = x.left;
        x.left = y.right;
        if (y.right != TNULL) {
            y.right.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.right) {
            x.parent.right = y;
        } else {
            x.parent.left = y;
        }
        y.right = x;
        x.parent = y;
    }

    // Metode rotasi ke kiri
    private void rotasiKiri(Node x){
        Node y = x.right;
        x.right = y.left;
        if (y.left != TNULL) {
            y.left.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
    }

    // Mencetak semua data plat nomor dalam urutan inorder
    public void cetakSemuaData() {
        System.out.println("Data Plat Nomor (Urutan Inorder):");
        inorderTraversal(root);
        System.out.println();
    }

    // Metode bantu untuk traversing inorder
    private void inorderTraversal(Node node) {
        if (node != TNULL) {
            inorderTraversal(node.left);
            System.out.println("Nomor Plat: " + node.data.nomorPlat + ", Nama Pemilik: " + node.data.namaPemilik);
            inorderTraversal(node.right);
        }
    }

    //Melakukan pencarian dan menampilkan hasilnya
    public void melakukanPencarian(String nomorPlat) {
        DataPlatNomor hasilPencarian = cari(nomorPlat);
        if (hasilPencarian != null) {
            System.out.println("Data ditemukan - Nomor Plat: " + hasilPencarian.nomorPlat + ", Nama Pemilik: " + hasilPencarian.namaPemilik);
        } else {
            System.out.println("Data tidak ditemukan untuk Nomor Plat: " + nomorPlat);
        }
    }

    // Metode pengujian
    public static void main(String[] args) {
        ManajemenPlatNomor manajer = new ManajemenPlatNomor();

        // Menambahkan beberapa data plat nomor
        manajer.sisip(new DataPlatNomor("B 1234 CD", "Anto"));
        manajer.sisip(new DataPlatNomor("A 5678 EF", "Yudi"));
        manajer.sisip(new DataPlatNomor("C 9101 GH", "Ekky"));

        // Menampilkan semua data plat nomor
        manajer.cetakSemuaData();

        // Melakukan pencarian
        manajer.melakukanPencarian("A 5678 EF");
        manajer.melakukanPencarian("X 9999 YY");
    }
}
