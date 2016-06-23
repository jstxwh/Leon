package entity;

import java.io.IOException;
import java.util.Map;

import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.MergePolicy;
import org.apache.lucene.index.MergeTrigger;
import org.apache.lucene.index.SegmentCommitInfo;
import org.apache.lucene.index.SegmentInfos;

public class MyMergePolicy extends MergePolicy {

	@Override
	public MergeSpecification findMerges(MergeTrigger mergeTrigger,
			SegmentInfos segmentInfos, IndexWriter writer) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MergeSpecification findForcedMerges(SegmentInfos segmentInfos,
			int maxSegmentCount,
			Map<SegmentCommitInfo, Boolean> segmentsToMerge, IndexWriter writer)
			throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MergeSpecification findForcedDeletesMerges(
			SegmentInfos segmentInfos, IndexWriter writer) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
