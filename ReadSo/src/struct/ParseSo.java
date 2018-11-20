package struct;

import struct.ElfType32;
import struct.ElfType32.*;
import utils.Elf32Util;
import utils.StringUtil;

import java.util.ArrayList;
import java.util.List;


public class ParseSo {
	
	public static ElfType32 type_32 = new ElfType32();

	public static ElfType32 parseSo(byte[] fileByteArys){
		System.out.println("+++++++++++++++++++Elf Header+++++++++++++++++");
		parseHeader(fileByteArys, 0);
		System.out.println("header:\n"+type_32.hdr);

		System.out.println();
		System.out.println("+++++++++++++++++++Program Header+++++++++++++++++");
		int offset = StringUtil.byte2Int(type_32.hdr.e_phoff);
		System.out.println("Program Header offset:"+offset);
		parseProgramHeaderList(fileByteArys, offset, type_32.hdr);
//		int sizeParse = type_32.phdrList.size();
//		int sizeGet = StringUtil.byte2Short(type_32.hdr.e_phnum);
//		System.out.println("Program Header size parse:"+sizeParse+", size get="+sizeGet);
//		type_32.printPhdrList();

		System.out.println();
		System.out.println("+++++++++++++++++++Section Header++++++++++++++++++");
		offset = StringUtil.byte2Int(type_32.hdr.e_shoff);
		System.out.println("Section Header offset:"+offset);
		parseSectionHeaderList(fileByteArys, offset);
//		sizeParse=type_32.shdrList.size();
//		sizeGet = StringUtil.byte2Short(type_32.hdr.e_shnum);
//		System.out.println("Program Header size parse:"+sizeParse+", size get="+sizeGet);
//		type_32.printShdrList();

		System.out.println();
		System.out.println("+++++++++++++++++++String Table++++++++++++++++++");
		int offset_str=0;
		int total_str=0;
		for(ELF32_shdr shdr : type_32.shdrList){
			if(StringUtil.byte2Int(shdr.sh_type) == ElfType32.SHT_STRTAB){
				total_str = StringUtil.byte2Int(shdr.sh_size);
				offset_str = StringUtil.byte2Int(shdr.sh_offset);
				int len_str = total_str;
				System.out.println("length num="+len_str+", offset="+offset_str);
				parseStringTableList(fileByteArys, len_str, offset_str);
			}
		}


		System.out.println();
		System.out.println("+++++++++++++++++++Symbol Table++++++++++++++++++");
		int offset_sym = 0;
		int total_sym = 0;
		for(ELF32_shdr shdr : type_32.shdrList){
			if(StringUtil.byte2Int(shdr.sh_type) == ElfType32.SHT_DYNSYM){
				total_sym = StringUtil.byte2Int(shdr.sh_size);
				offset_sym = StringUtil.byte2Int(shdr.sh_offset);
				int num_sym = total_sym / 16;
				parseSymbolTableList(fileByteArys, num_sym, offset_sym);
//				Elf32Util.printSymList(type_32.symList);
				break;
			}
		}

		System.out.println();
		System.out.println("+++++++++++++++++++Hash Table++++++++++++++++++");
		int offset_hash = 0;
		int total_hash = 0;
		for(ELF32_shdr shdr : type_32.shdrList){
			if(StringUtil.byte2Int(shdr.sh_type) == ElfType32.SHT_HASH){
				total_hash = StringUtil.byte2Int(shdr.sh_size);
				offset_hash = StringUtil.byte2Int(shdr.sh_offset);
				parseHashTable(fileByteArys, total_hash, offset_hash);
				Elf32Util.printHashTable(type_32.hashtb);
			}
		}
		System.out.println();
		return type_32;

	}
	private static void  parseHeader(byte[] header, int offset){
		if(header == null){
			System.out.println("header is null");
			return;
		}
		int cur=0;
		type_32.hdr.e_ident = StringUtil.copyBytes(header, cur, type_32.hdr.e_ident.length);
		cur+=type_32.hdr.e_ident.length;
		type_32.hdr.e_type = StringUtil.copyBytes(header, cur, type_32.hdr.e_type.length);
        cur+=type_32.hdr.e_type.length;
		type_32.hdr.e_machine = StringUtil.copyBytes(header, cur, type_32.hdr.e_machine.length);
        cur+=type_32.hdr.e_machine.length;
		type_32.hdr.e_version = StringUtil.copyBytes(header, cur, type_32.hdr.e_version.length);
        cur+=type_32.hdr.e_version.length;
		type_32.hdr.e_entry = StringUtil.copyBytes(header, cur, type_32.hdr.e_entry.length);
        cur+=type_32.hdr.e_entry.length;
		type_32.hdr.e_phoff = StringUtil.copyBytes(header, cur, type_32.hdr.e_phoff.length);
        cur+=type_32.hdr.e_phoff.length;
		type_32.hdr.e_shoff = StringUtil.copyBytes(header, cur, type_32.hdr.e_shoff.length);
		cur+=type_32.hdr.e_shoff.length;
		type_32.hdr.e_flags = StringUtil.copyBytes(header, cur, type_32.hdr.e_flags.length);
        cur+=type_32.hdr.e_flags.length;
		type_32.hdr.e_ehsize = StringUtil.copyBytes(header, cur, type_32.hdr.e_ehsize.length);
        cur+=type_32.hdr.e_ehsize.length;
		type_32.hdr.e_phentsize = StringUtil.copyBytes(header, cur, type_32.hdr.e_phentsize.length);
        cur+=type_32.hdr.e_phentsize.length;
        type_32.hdr.e_phnum = StringUtil.copyBytes(header, cur,type_32.hdr.e_phnum.length);
        cur+=type_32.hdr.e_phnum.length;
        type_32.hdr.e_shentsize = StringUtil.copyBytes(header, cur,type_32.hdr.e_shentsize.length);
        cur+=type_32.hdr.e_shentsize.length;
        type_32.hdr.e_shnum = StringUtil.copyBytes(header, cur, type_32.hdr.e_shnum.length);
        cur+=type_32.hdr.e_shnum.length;
        type_32.hdr.e_shstrndx = StringUtil.copyBytes(header, cur, type_32.hdr.e_shstrndx.length);
        cur+=type_32.hdr.e_shstrndx.length;
	}
	
	public static void parseProgramHeaderList(byte[] header, int offset, ELF32_hdr hdr){
		int header_size = 32;
		int header_count = StringUtil.byte2Short(hdr.e_phnum);
		byte[] des = new byte[header_size];
		for(int i=0;i<header_count;i++){
			System.arraycopy(header, i*header_size + offset, des, 0, header_size);
			type_32.phdrList.add(parseProgramHeader(des));
		}
	}
	
	private static ELF32_phdr parseProgramHeader(byte[] header){
		/**
		 *  public int p_type;
			public int p_offset;
			public int p_vaddr;
			public int p_paddr;
			public int p_filesz;
			public int p_memsz;
			public int p_flags;
			public int p_align;
		 */
		ELF32_phdr phdr = type_32.new ELF32_phdr();
		phdr.p_type = StringUtil.copyBytes(header, 0, 4);
		phdr.p_offset = StringUtil.copyBytes(header, 4, 4);
		phdr.p_vaddr = StringUtil.copyBytes(header, 8, 4);
		phdr.p_paddr = StringUtil.copyBytes(header, 12, 4);
		phdr.p_filesz = StringUtil.copyBytes(header, 16, 4);
		phdr.p_memsz = StringUtil.copyBytes(header, 20, 4);
		phdr.p_flags = StringUtil.copyBytes(header, 24, 4);
		phdr.p_align = StringUtil.copyBytes(header, 28, 4);
		return phdr;
		
	}

	public static void parseSectionHeaderList(byte[] header, int offset){
		int header_size = 40;
		int header_count = StringUtil.byte2Short(type_32.hdr.e_shnum);
		byte[] des = new byte[header_size];
		for(int i=0;i<header_count;i++){
			System.arraycopy(header, i*header_size + offset, des, 0, header_size);
			type_32.shdrList.add(parseSectionHeader(des));
		}
	}
	
	private static ELF32_shdr parseSectionHeader(byte[] header){
		ELF32_shdr shdr = type_32.new ELF32_shdr();
		/**
		 *  public byte[] sh_name = new byte[4];
			public byte[] sh_type = new byte[4];
			public byte[] sh_flags = new byte[4];
			public byte[] sh_addr = new byte[4];
			public byte[] sh_offset = new byte[4];
			public byte[] sh_size = new byte[4];
			public byte[] sh_link = new byte[4];
			public byte[] sh_info = new byte[4];
			public byte[] sh_addralign = new byte[4];
			public byte[] sh_entsize = new byte[4];
		 */
		shdr.sh_name = StringUtil.copyBytes(header, 0, 4);
		shdr.sh_type = StringUtil.copyBytes(header, 4, 4);
		shdr.sh_flags = StringUtil.copyBytes(header, 8, 4);
		shdr.sh_addr = StringUtil.copyBytes(header, 12, 4);
		shdr.sh_offset = StringUtil.copyBytes(header, 16, 4);
		shdr.sh_size = StringUtil.copyBytes(header, 20, 4);
		shdr.sh_link = StringUtil.copyBytes(header, 24, 4);
		shdr.sh_info = StringUtil.copyBytes(header, 28, 4);
		shdr.sh_addralign = StringUtil.copyBytes(header, 32, 4);
		shdr.sh_entsize = StringUtil.copyBytes(header, 36, 4);
		return shdr;
	}

	private static void parseStringTableList(byte[] header, int length, int offset){
		byte[] des=new byte[length];
		List<ELF32_strtb> strList=new ArrayList<>();
		System.arraycopy(header, offset, des, 0, length);
		for (int i = 0; i < length; i++) {
			ELF32_strtb strtb=type_32. new ELF32_strtb();
			strtb.str_index=i;
			for (int j = strtb.str_index; j < length; j++) {
				if (des[j]==0x0){
					strtb.len=j-strtb.str_index+1;
					strtb.str_name=StringUtil.copyBytes(des, strtb.str_index, strtb.len);
					i=j;
					break;
				}
			}
			type_32.strtbList.add(strtb);
			strList.add(strtb);
		}
		type_32.strtbMap.put(offset, strList);
	}

	private static void parseSymbolTableList(byte[] header, int header_count, int offset){
		int header_size = 16;
		byte[] des = new byte[header_size];
		for(int i=0;i<header_count;i++){
			System.arraycopy(header, i*header_size + offset, des, 0, header_size);
			Elf32_sym elf32_sym = parseSymbolTable(des);
			elf32_sym.elf_offset=offset+i;
			type_32.symList.add(elf32_sym);
		}
	}
	
	private static Elf32_sym parseSymbolTable(byte[] header){
		/**
		 *  public byte[] st_name = new byte[4];
			public byte[] st_value = new byte[4];
			public byte[] st_size = new byte[4];
			public byte st_info;
			public byte st_other;
			public byte[] st_shndx = new byte[2];
		 */
		Elf32_sym sym = type_32.new Elf32_sym();
		sym.st_name = StringUtil.copyBytes(header, 0, 4);
		sym.st_value = StringUtil.copyBytes(header, 4, 4);
		sym.st_size = StringUtil.copyBytes(header, 8, 4);
		sym.st_info = header[12];

		sym.st_other = header[13];
		sym.st_shndx = StringUtil.copyBytes(header, 14, 2);
		return sym;
	}
	private static int parseHashTable(byte[] header, int length, int offset){
		byte[] des = new byte[length];
		System.arraycopy(header, offset, des, 0, length);
		int cur=0;
		type_32.hashtb.elf_offset=offset;
		type_32.hashtb.nbucket=StringUtil.copyBytes(des, cur, type_32.hashtb.nbucket.length);
		cur+=type_32.hashtb.nbucket.length;
		type_32.hashtb.nchain=StringUtil.copyBytes(des, cur, type_32.hashtb.nchain.length);
		cur+=type_32.hashtb.nchain.length;
		if (length%4!=0||(2+StringUtil.byte2Int(type_32.hashtb.nbucket)+StringUtil.byte2Int(type_32.hashtb.nchain))!=length/4){
			System.out.println("!!!!!!!!Error:parseHashTable() not hash table bytes!!!!!!!!!!!!!!!!");
			return -1;
		}
		byte[] bucket = type_32.hashtb.createOneBucket();
		int whileLength=cur+StringUtil.byte2Int(type_32.hashtb.nbucket)*bucket.length;
		while (cur<whileLength){
			bucket=StringUtil.copyBytes(des, cur, bucket.length);
			type_32.hashtb.bucket.add(bucket);
			cur+=bucket.length;
		}
		byte[] chain = type_32.hashtb.createOneChain();
		whileLength=cur+StringUtil.byte2Int(type_32.hashtb.nchain)*chain.length;
		while (cur<whileLength){
			chain=StringUtil.copyBytes(des, cur, chain.length);
			type_32.hashtb.chain.add(chain);
			cur+=chain.length;
		}
		return 0;
	}


}
