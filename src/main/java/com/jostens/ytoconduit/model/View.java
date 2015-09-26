package com.jostens.ytoconduit.model;

public class View {

	static class Public { }
    //static class ExtendedPublic extends PublicView { }
    //static class Internal extends ExtendedPublicView { }
	public interface Summary {}
	public interface SummaryWithRecipients extends Summary {}
	
	public interface DefinitionSummary{}
	public interface ImageList{}
	public interface ImageUploadSummary{};
	public interface DesignPublishSummary{};
}
