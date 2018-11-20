package struct;

import utils.Elf32Util;
import utils.StringUtil;

import java.util.*;


public class ElfType32 extends ElfTypeBase{

	public ELF32_hdr hdr;
	public ArrayList<ELF32_phdr> phdrList;

	public ArrayList<ELF32_shdr> shdrList;

	public ELF32_rel rel;
	public ELF32_rela rela;
	public ArrayList<Elf32_sym> symList;
	public Map<Integer, List<ELF32_strtb>> strtbMap;
	@Deprecated
	public ArrayList<ELF32_strtb> strtbList;
	public ELF32_hashtb hashtb;

	
	public ElfType32() {
		rel = new ELF32_rel();
		rela = new ELF32_rela();
		hdr = new ELF32_hdr();
		hashtb=new ELF32_hashtb();
		phdrList = new ArrayList<>();
		shdrList = new ArrayList<>();
		symList = new ArrayList<>();
		strtbList = new ArrayList<>();
		strtbMap=new HashMap<>();
	}



	/**
	 * typedef struct ELF32_hdr{
	 unsigned char	e_ident[EI_NIDENT];
	 Elf32_Half	e_type;
	 Elf32_Half	e_machine;
	 Elf32_Word	e_version;
	 Elf32_Addr	e_entry; 程序入口的虚拟地址
	 Elf32_Off	e_phoff; 程序头(Program Header)内容在整个文件的偏移值，我们可以用这个偏移值来定位程序头的开始位置，用于解析程序头信息
	 Elf32_Off	e_shoff; 段头(Section Header)内容在这个文件的偏移值，我们可以用这个偏移值来定位段头的开始位置，用于解析段头信息
	 Elf32_Word	e_flags;
	 Elf32_Half	e_ehsize; ELF 头部的大小（以字节计算）
	 Elf32_Half	e_phentsize; 程序头的表项大小（按字节计算）
	 Elf32_Half	e_phnum; 程序头的个数，用于解析程序头信息
	 Elf32_Half	e_shentsize;段头的表项大小（按字节计算）
	 Elf32_Half	e_shnum; 段头的个数，用于解析段头信息
	 Elf32_Half	e_shstrndx; string段头的个数，用于解析段头信息
	 } Elf32_Ehdr;
	 */
	public class ELF32_hdr {
		public byte[] e_ident = new byte[EI_NIDENT];
		public byte[] e_type = new byte[Elf32_Half];
		public byte[] e_machine = new byte[Elf32_Half];
		public byte[] e_version = new byte[Elf32_Word];
		public byte[] e_entry = new byte[Elf32_Addr];
		public byte[] e_phoff = new byte[Elf32_Off];
		public byte[] e_shoff = new byte[Elf32_Off];
		public byte[] e_flags = new byte[Elf32_Word];
		public byte[] e_ehsize = new byte[Elf32_Half];
		public byte[] e_phentsize = new byte[Elf32_Half];
		public byte[] e_phnum = new byte[Elf32_Half];
		public byte[] e_shentsize = new byte[Elf32_Half];
		public byte[] e_shnum = new byte[Elf32_Half];
		public byte[] e_shstrndx = new byte[Elf32_Half];

		@Override
		public String toString(){
			return  "magic:"+ StringUtil.bytes2HexString(e_ident)
					+"\ne_type:"+ StringUtil.bytes2HexString(e_type)
					+"\ne_machine:"+StringUtil.bytes2HexString(e_machine)
					+"\ne_version:"+StringUtil.bytes2HexString(e_version)
					+"\ne_entry:"+StringUtil.bytes2HexString(e_entry)
					+"\ne_phoff:"+StringUtil.bytes2HexString(e_phoff)
					+"\ne_shoff:"+StringUtil.bytes2HexString(e_shoff)
					+"\ne_flags:"+StringUtil.bytes2HexString(e_flags)
					+"\ne_ehsize:"+StringUtil.bytes2HexString(e_ehsize)
					+"\ne_phentsize:"+StringUtil.bytes2HexString(e_phentsize)
					+"\ne_phnum:"+StringUtil.bytes2HexString(e_phnum)
					+"\ne_shentsize:"+StringUtil.bytes2HexString(e_shentsize)
					+"\ne_shnum:"+StringUtil.bytes2HexString(e_shnum)
					+"\ne_shstrndx:"+StringUtil.bytes2HexString(e_shstrndx);
		}
	}


	/**
	 * typedef struct ELF32_phdr{
	 Elf32_Word	p_type;
	 Elf32_Off	p_offset;
	 Elf32_Addr	p_vaddr;
	 Elf32_Addr	p_paddr;
	 Elf32_Word	p_filesz;
	 Elf32_Word	p_memsz;
	 Elf32_Word	p_flags;
	 Elf32_Word	p_align;
	 } Elf32_Phdr;
	 */
	public class ELF32_phdr {
		public byte[] p_type = new byte[Elf32_Word];
		public byte[] p_offset = new byte[Elf32_Off];
		public byte[] p_vaddr = new byte[Elf32_Addr];
		public byte[] p_paddr = new byte[Elf32_Addr];
		public byte[] p_filesz = new byte[Elf32_Word];
		public byte[] p_memsz = new byte[Elf32_Word];
		public byte[] p_flags = new byte[Elf32_Word];
		public byte[] p_align = new byte[Elf32_Word];

		@Override
		public String toString(){
			return "p_type:"+ StringUtil.bytes2HexString(p_type)
					+"\np_offset:"+StringUtil.bytes2HexString(p_offset)
					+"\np_vaddr:"+StringUtil.bytes2HexString(p_vaddr)
					+"\np_paddr:"+StringUtil.bytes2HexString(p_paddr)
					+"\np_filesz:"+StringUtil.bytes2HexString(p_filesz)
					+"\np_memsz:"+StringUtil.bytes2HexString(p_memsz)
					+"\np_flags:"+StringUtil.bytes2HexString(p_flags)
					+"\np_align:"+StringUtil.bytes2HexString(p_align);
		}
	}

	/**
	 * typedef struct ELF32_shdr {
	 Elf32_Word	sh_name; 节区名称。是节区头部字符串表节区的索引
	 Elf32_Word	sh_type; 为节区的内容和语义进行分类
	 Elf32_Word	sh_flags; 节区支持 1 位形式的标志，这些标志描述了多种属性。
	 Elf32_Addr	sh_addr; 如果节区将出现在进程的内存映像中， 此成员给出节区的第一个字节应处的位置。否则，此字段为 0。
	 Elf32_Off	sh_offset; 节区的第一个字节与文件头之间的偏移
	 						(SHT_NOBITS 类型的节区不占用文件的空间， 因此其 sh_offset 成员给出的是其概念性的偏移)
	 Elf32_Word	sh_size; 节区的长度（字节数）
	 						(类型为SHT_NOBITS 的节区长度可能非零， 不过却不占用文件中的空间。)
	 Elf32_Word	sh_link; 节区头部表索引链接
	 Elf32_Word	sh_info; 附加信息，其解释依赖于节区类型
	 Elf32_Word	sh_addralign; 某些节区带有地址对齐约束
	 Elf32_Word	sh_entsize; 某些节区中包含固定大小的项目， 如符号表
	 } Elf32_Shdr;
	 */
	public class ELF32_shdr {
		public byte[] sh_name = new byte[Elf32_Word];
		public byte[] sh_type = new byte[Elf32_Word];
		public byte[] sh_flags = new byte[Elf32_Word];
		public byte[] sh_addr = new byte[Elf32_Addr];
		public byte[] sh_offset = new byte[Elf32_Off];
		public byte[] sh_size = new byte[Elf32_Word];
		public byte[] sh_link = new byte[Elf32_Word];
		public byte[] sh_info = new byte[Elf32_Word];
		public byte[] sh_addralign = new byte[Elf32_Word];
		public byte[] sh_entsize = new byte[Elf32_Word];

		@Override
		public String toString(){
			return "sh_name:"+StringUtil.bytes2HexString(sh_name)/*Utils.byte2Int(sh_name)*/
					+"\nsh_type:"+StringUtil.bytes2HexString(sh_type)
					+"\nsh_flags:"+StringUtil.bytes2HexString(sh_flags)
					+"\nsh_add:"+StringUtil.bytes2HexString(sh_addr)
					+"\nsh_offset:"+StringUtil.bytes2HexString(sh_offset)
					+"\nsh_size:"+StringUtil.bytes2HexString(sh_size)
					+"\nsh_link:"+StringUtil.bytes2HexString(sh_link)
					+"\nsh_info:"+StringUtil.bytes2HexString(sh_info)
					+"\nsh_addralign:"+StringUtil.bytes2HexString(sh_addralign)
					+"\nsh_entsize:"+ StringUtil.bytes2HexString(sh_entsize);
		}
	}

	/**
	 * typedef struct elf32_sym{
	 Elf32_Word	st_name;
	 Elf32_Addr	st_value;
	 Elf32_Word	st_size;
	 unsigned char	st_info;
	 unsigned char	st_other;
	 Elf32_Half	st_shndx;
	 } Elf32_sym;
	 */
	public class Elf32_sym {
		public int elf_offset;
		public byte[] st_name = new byte[Elf32_Word];
		public byte[] st_value = new byte[Elf32_Addr];
		public byte[] st_size = new byte[Elf32_Word];
		public byte st_info;
		public byte st_other;
		public byte[] st_shndx = new byte[Elf32_Half];

		@Override
		public String toString(){
//			ELF32_strtb strName = Elf32Util.getStrByIndex(strtbList, StringUtil.byte2Int(st_name));
			ELF32_strtb strName = Elf32Util.getStrByIndex(strtbMap, StringUtil.byte2Int(st_name));
			return "elf_offset:"+elf_offset
					+"\nst_name:"+(strName==null?StringUtil.bytes2HexString(st_name):strName)
					+"\nst_value:"+StringUtil.bytes2HexString(st_value)
					+"\nst_size:"+StringUtil.bytes2HexString(st_size)
					+"\nst_info:"+(st_info/16)
					+"\nst_other:"+(((short)st_other) & 0xF)
					+"\nst_shndx:"+StringUtil.bytes2HexString(st_shndx);
		}
	}

	public class ELF32_strtb {
		public int str_index;
		public byte[] str_name;
		public int len;

		@Override
		public String toString() {
			return "{" +
					"str_index=" + str_index +
					", str_name=" + StringUtil.bytes2UTF8String(str_name) +
					", len=" + len +
					'}';
		}
	}

	/**
	 *  typedef struct ELF32_rel {
		  Elf32_Addr	r_offset;
		  Elf32_Word	r_info;
		} Elf32_Rel;
	 *
	 */
	public class ELF32_rel {
		public byte[] r_offset = new byte[Elf32_Addr];
		public byte[] r_info = new byte[Elf32_Word];
		
		@Override
		public String toString(){
			return "r_offset:"+StringUtil.bytes2HexString(r_offset)+";r_info:"+StringUtil.bytes2HexString(r_info);
		}
	}
	
	/**
	 *  typedef struct ELF32_rela{
		  Elf32_Addr	r_offset;
		  Elf32_Word	r_info;
		  Elf32_Sword	r_addend;
		} Elf32_Rela;
	 */
	public class ELF32_rela {
		public byte[] r_offset = new byte[Elf32_Addr];
		public byte[] r_info = new byte[Elf32_Word];
		public byte[] r_addend = new byte[Elf32_Word];
		
		@Override
		public String toString(){
			return "r_offset:"+StringUtil.bytes2HexString(r_offset)+";r_info:"+StringUtil.bytes2HexString(r_info)+";r_addend:"+StringUtil.bytes2HexString(r_addend);
		}
	}

	/**
	 *  typedef struct ELF32_hashtb{
	 Elf32_Word	nbucket;
	 Elf32_Word	nchain;
	 Elf32_Word[]	bucket;
	 Elf32_Word[] chain;
	 } Elf32_Rela;
	 */
	public class ELF32_hashtb{
		public int elf_offset=0;
		public byte[] nbucket=new byte[Elf32_Word];
		public byte[] nchain=new byte[Elf32_Word];
		public ArrayList<byte[]> bucket=new ArrayList<>();
		public ArrayList<byte[]> chain=new ArrayList<>();
		public int getOneBucketLength(){
			return Elf32_Word;
		}
		public int getOneChainLength(){
			return Elf32_Word;
		}
		public byte[] createOneBucket(){
			return new byte[getOneBucketLength()];
		}
		public byte[] createOneChain(){
			return new byte[getOneChainLength()];
		}

		private String bucketToString(){
			StringBuilder sb=new StringBuilder();
			for (byte[] aBucket : bucket) {
				sb.append("<");
				sb.append(StringUtil.bytes2HexString(aBucket));
				sb.append(">\t");
			}
			return sb.toString();
		}
		private String chainToString(){
			StringBuilder sb=new StringBuilder();
			for (byte[] aChain : chain) {
				sb.append("<");
				sb.append(StringUtil.bytes2HexString(aChain));
				sb.append(">\t");
			}
			return sb.toString();
		}
		@Override
		public String toString() {
			return "elf_offset="+ elf_offset+
					"\nnbucket=" + StringUtil.byte2Int(nbucket) +
					"\nnchain=" + StringUtil.byte2Int(nchain) +
					"\nbucket:" + bucketToString() +
					"\nchain:" + chainToString();
		}
	}

	public static final int STN_UNDEF=0;

	public static final int STB_LOCAL = 0;
	public static final int STB_GLOBAL = 1;
	public static final int STB_WEAK = 2;

	public static final int STT_NOTYPE = 0;
	public static final int STT_OBJECT = 1;
	public static final int STT_FUNC = 2;
	public static final int STT_SECTION = 3;
	public static final int STT_FILE = 4;

	/****************节区类型sh_type********************/
	public static final int SHT_NULL = 0;  //此值标志节区头部是非活动的，没有对应的节区
	public static final int SHT_PROGBITS = 1;  //包含程序定义的信息，其格式和含义都由程序来解释
	public static final int SHT_SYMTAB = 2;  //用于链接编辑（指 ld 而言）的符号表,目前只能包含一个，不过将来可能发生变化
	public static final int SHT_STRTAB = 3;  //字符串表,可能包含多个
	public static final int SHT_RELA = 4;  //重定位表项， 其中可能会有补齐内容(addend),可能包含多个
	public static final int SHT_HASH = 5;  //符号哈希表。所有动态链接的目标都必须包含一个符号哈希表。目前只包含一个,不过将来可能解除
	public static final int SHT_DYNAMIC = 6; //动态链接的信息,目前只包含一个,不过将来可能解除
	public static final int SHT_NOTE = 7;  //节区包含以某种方式来标记文件的信息
	public static final int SHT_NOBITS = 8;  //节区不占用文件中的空间，其他方面和SHT_PROGBITS 相似
	public static final int SHT_REL = 9;  //重定位表项， 其中没有补齐（addends）,可以拥有多个
	public static final int SHT_SHLIB = 10;  //节区被保留，不过其语义是未规定的.包含此类型节区的程序与 ABI 不兼容。
	public static final int SHT_DYNSYM = 11;  //完整的符号表，它可能包含很多对动态链接而言不必要的符号
	public static final int SHT_NUM = 12;
	public static final int SHT_LOPROC = 0x70000000;  //这一段（包括两个边界），是保留给处理器专用语义的
	public static final int SHT_HIPROC = 0x7fffffff;  //这一段（包括两个边界），是保留给处理器专用语义的
	public static final int SHT_LOUSER = 0x80000000;  //此值给出保留给应用程序的索引下界
	public static final int SHT_HIUSER = 0xffffffff;  //此值给出保留给应用程序的索引上界
	public static final int SHT_MIPS_LIST = 0x70000000;
	public static final int SHT_MIPS_CONFLICT = 0x70000002;
	public static final int SHT_MIPS_GPTAB = 0x70000003;
	public static final int SHT_MIPS_UCODE = 0x70000004;
	
	/*****************sh_flag***********************/
	public static final int SHF_WRITE = 0x1;
	public static final int SHF_ALLOC = 0x2;
	public static final int SHF_EXECINSTR = 0x4;
	public static final int SHF_MASKPROC = 0xf0000000;
	public static final int SHF_MIPS_GPREL = 0x10000000;

}
